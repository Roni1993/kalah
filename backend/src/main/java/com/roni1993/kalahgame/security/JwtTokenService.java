package com.roni1993.kalahgame.security;

import com.roni1993.kalahgame.dto.PlayerDto;
import com.roni1993.kalahgame.model.Player;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtTokenService {
    public static final String JWT_SUBJECT = "sub";
    public static final String JWT_PLAYER = "player";
    public static final String JWT_GAMEID = "gameid";

    @Value("${kalah.jwt.secret}")
    private byte[] JWT_SECRET;
    @Value("${kalah.jwt.expiration:60}")
    private Integer JWT_EXPIRATION;

    private JwtParser parser;

    @PostConstruct
    private void init() {
        parser = Jwts.parserBuilder().setSigningKey(JWT_SECRET).build();
    }

    public String generateToken(Player position, UUID gameId, String name) {
        Instant expirationTime = Instant.now().plus(JWT_EXPIRATION, ChronoUnit.MINUTES);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(JWT_SECRET);

        return Jwts.builder()
                .claim(JWT_SUBJECT, name)
                .claim(JWT_PLAYER, position)
                .claim(JWT_GAMEID, gameId)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateTokenForHeader(Player position, UUID gameId, String name) {
        return "Bearer " + generateToken(position, gameId, name);
    }

    public Authentication parseToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Jws<Claims> jwsClaims = parser.parseClaimsJws(token);
        Claims claims = jwsClaims.getBody();
        var role = new SimpleGrantedAuthority("ROLE_PLAYER");

        var auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, List.of(role));
        auth.setDetails(PlayerDto.builder().token("")
                .gameId(UUID.fromString(claims.get(JWT_GAMEID,String.class)))
                .position(Player.valueOf(claims.get(JWT_PLAYER, String.class))).build());
        return auth;
    }
}
