package com.pavliuchenko.jwtauthservice.service.jwt;

public interface JwtService {


    boolean isValidToken(String token);
    String createToken();
}
