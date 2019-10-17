package com.pavliuchenko.jwtauthservice.config.auth.basic;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

public class BasicAuthWebFilter extends AuthenticationWebFilter {
    /**
     * Creates an instance
     *
     * @param authenticationManager the authentication manager to use
     */
    public BasicAuthWebFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
}
