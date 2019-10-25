package com.pavliuchenko.jwtauthservice.config.auth.jwt;

import com.pavliuchenko.jwtauthservice.service.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class JwtToAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtService jwtService;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return null;
    }
}
