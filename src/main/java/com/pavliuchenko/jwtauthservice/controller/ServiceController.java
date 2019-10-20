package com.pavliuchenko.jwtauthservice.controller;

import com.pavliuchenko.jwtauthservice.service.key.KeyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("/service/public")
@AllArgsConstructor
public class ServiceController {

    private final KeyService keyService;

    @GetMapping
    public Mono<byte[]> getPublicKey() {
        return keyService.getEncodedPublicKey();
    }
}
