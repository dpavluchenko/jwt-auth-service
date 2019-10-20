package com.pavliuchenko.jwtauthservice.controller;

import com.pavliuchenko.jwtauthservice.controller.dto.SignIn;
import com.pavliuchenko.jwtauthservice.controller.dto.SignUp;
import com.pavliuchenko.jwtauthservice.controller.dto.UserInfo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class UserController {


    @PostMapping(value = "/sign-in", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Mono<ResponseEntity> signIn(@RequestBody SignIn model) {
        return Mono.empty();
    }

    @PostMapping("/sign-up")
    public Mono<ResponseEntity> signUp(@RequestBody SignUp model) {
        return Mono.empty();
    }

    @GetMapping("/user-info")
    public Mono<UserInfo> getUserInfo(@RequestParam String id) {
        return Mono.empty();
    }


}
