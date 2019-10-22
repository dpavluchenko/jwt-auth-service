package com.pavliuchenko.jwtauthservice.service.jwt;

import com.pavliuchenko.jwtauthservice.domain.User;

public interface JwtService {


    boolean isValidToken(String token);
    String createTokenForUser(User user);
}
