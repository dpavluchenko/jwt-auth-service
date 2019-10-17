package com.pavliuchenko.jwtauthservice.config.auth.basic;

import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BasicAuthCustomManager extends UserDetailsRepositoryReactiveAuthenticationManager {


    public BasicAuthCustomManager(ReactiveUserDetailsService userDetailsService) {
        super(userDetailsService);
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return super.authenticate(authentication);
    }
}
