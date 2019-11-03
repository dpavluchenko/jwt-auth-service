package com.pavliuchenko.jwtauthservice.controller;

import com.pavliuchenko.jwtauthservice.controller.dto.SignIn;
import com.pavliuchenko.jwtauthservice.controller.dto.SignUp;
import com.pavliuchenko.jwtauthservice.service.dto.UserInfo;
import com.pavliuchenko.jwtauthservice.domain.User;
import com.pavliuchenko.jwtauthservice.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/sign-in", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Mono<Void> signIn(@RequestBody SignIn model) {
        return Mono.empty();
    }

    @PostMapping("/sign-up")
    public Mono<Void> signUp(@RequestBody SignUp model) {
        return userService.create(model.getUsername(), model.getPassword(), model.getFullName());
    }

    @GetMapping("/user-info")
    public Mono<UserInfo> getUserInfo(@AuthenticationPrincipal User user) {
        return userService.getUserInfo(user.getId());
    }


}
