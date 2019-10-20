package com.pavliuchenko.jwtauthservice.service.user;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> create();
}
