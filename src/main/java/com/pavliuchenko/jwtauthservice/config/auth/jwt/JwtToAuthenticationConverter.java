package com.pavliuchenko.jwtauthservice.config.auth.jwt;

import com.pavliuchenko.jwtauthservice.domain.JwtInfo;
import com.pavliuchenko.jwtauthservice.exception.JwtNotValidException;
import com.pavliuchenko.jwtauthservice.service.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class JwtToAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtService jwtService;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return jwtService
                .validateToken(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new JwtNotValidException("JWT isn't valid!"))))
                .map(JwtInfo::toUser)
                .map(user -> new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }
}
