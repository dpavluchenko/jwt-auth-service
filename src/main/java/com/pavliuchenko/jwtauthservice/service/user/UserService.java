package com.pavliuchenko.jwtauthservice.service.user;

import com.pavliuchenko.jwtauthservice.service.dto.UserInfo;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> create(String username, String password, String fullName);
    Mono<UserInfo> getUserInfo(String id);
}
