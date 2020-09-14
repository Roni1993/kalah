package com.roni1993.kalahgame.api;

import com.roni1993.kalahgame.dto.GameDto;
import com.roni1993.kalahgame.dto.PlayAreaDto;
import com.roni1993.kalahgame.dto.PlayerDto;
import com.roni1993.kalahgame.logic.GameManager;
import com.roni1993.kalahgame.logic.GameStateManager;
import com.roni1993.kalahgame.model.GameRuleViolationException;
import com.roni1993.kalahgame.model.PlayerInfo;
import com.roni1993.kalahgame.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    GameManager manager;

    @Autowired
    JwtTokenService tokenService;

    @GetMapping
    public ResponseEntity<List<GameDto>> getGames()
    {
        return ResponseEntity.ok(manager.getGames());
    }

    @PostMapping
    public ResponseEntity<GameDto> createGame()
    {
        return ResponseEntity.ok(manager.createGame());
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<PlayerDto> joinGame(@PathVariable("id") UUID id, @RequestBody PlayerInfo player) {
        var position = manager.joinGame(id, player);
        var token = tokenService.generateToken(position, id, player.getName());
        var joined = PlayerDto.builder().position(position).token(token).gameId(id).build();
        return ResponseEntity.ok(joined);
    }

    @PostMapping("/{id}/sow/{house}")
    @PreAuthorize("hasRole('ROLE_PLAYER')")
    public ResponseEntity sow(@PathVariable("id") UUID id, @PathVariable("house") int houseNumber, Authentication auth)
    {
        if (auth == null || !(auth.getDetails() instanceof PlayerDto))
            throw new GameRuleViolationException("Couldn't authorize the player");

        var player = (PlayerDto) auth.getDetails();
        //TODO check if UUID of token aligns with path UUID
        getGameSafe(id).sow(player.getPosition(),houseNumber);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/state")
    public ResponseEntity<GameDto> getGamestate(@PathVariable("id") UUID id)
    {
        return ResponseEntity.ok(getGameSafe(id).getGameDto());
    }

    @GetMapping("/{id}/playarea")
    public ResponseEntity<PlayAreaDto> getPlayArea(@PathVariable("id") UUID id)
    {
        return ResponseEntity.ok(getGameSafe(id).getPlayAreaDto());
    }

    private GameStateManager getGameSafe(@PathVariable("id") UUID id) {
        var game = manager.getGame(id);
        Objects.requireNonNull(game,"No Game was found with this ID. Aborting!");
        return game;
    }
}
