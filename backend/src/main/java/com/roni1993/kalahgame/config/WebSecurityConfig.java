package com.roni1993.kalahgame.config;


import com.roni1993.kalahgame.security.JwtAuthenticationFilter;
import com.roni1993.kalahgame.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests().antMatchers("/swagger-ui/**", "swagger-ui.html", "/v3/**").permitAll()
            .and().authorizeRequests().antMatchers("/game/*/sow").authenticated()
            .and().addFilterBefore(new JwtAuthenticationFilter(tokenService),
            UsernamePasswordAuthenticationFilter.class);
    }
}