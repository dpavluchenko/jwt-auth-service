package com.pavliuchenko.jwtauthservice.config.auth.basic;

import com.pavliuchenko.jwtauthservice.domain.User;
import com.pavliuchenko.jwtauthservice.service.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class BasicAuthSuccessHandler implements ServerAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        return webFilterExchange.getChain()
                .filter(getExchangeWithAuthHeader(webFilterExchange.getExchange(), (User) authentication.getPrincipal()));
    }

    private ServerWebExchange getExchangeWithAuthHeader(ServerWebExchange exchange, User user) {
        exchange
                .getResponse()
                .getHeaders()
                .add(HttpHeaders.AUTHORIZATION,
                        String.join(" ", "Bearer", jwtService.createTokenForUser(user)));
        return exchange;
    }
}
