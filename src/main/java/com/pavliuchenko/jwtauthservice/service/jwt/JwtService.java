package com.pavliuchenko.jwtauthservice.service.jwt;

import com.pavliuchenko.jwtauthservice.domain.JwtInfo;
import com.pavliuchenko.jwtauthservice.domain.User;
import reactor.core.publisher.Mono;

public interface JwtService {


    Mono<JwtInfo> validateToken(String token);
    String createTokenForUser(User user);
}
