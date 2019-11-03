package com.pavliuchenko.jwtauthservice.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtNotValidException extends AuthenticationException {

    public JwtNotValidException(String msg) {
        super(msg);
    }
}
