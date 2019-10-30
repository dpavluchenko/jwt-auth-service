package com.pavliuchenko.jwtauthservice.service.jwt;

import com.pavliuchenko.jwtauthservice.domain.User;
import io.jsonwebtoken.Jwt;
import reactor.core.publisher.Mono;

public interface JwtService {


    Mono<Jwt> validateToken(String token);
    Mono<User> getFromToken(Jwt token);
    String createTokenForUser(User user);
}
