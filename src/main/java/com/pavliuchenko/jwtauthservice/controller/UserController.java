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
    Mono<ResponseEntity> signIn(@RequestBody SignIn model) {

    }

    @PostMapping("/sign-up")
    Mono<ResponseEntity> signUp(@RequestBody SignUp model) {

    }

    @GetMapping("/user-info")
    Mono<UserInfo> getUserInfo(@RequestParam String id) {
    }
}
