package com.pavliuchenko.jwtauthservice.controller.dto;

import lombok.Data;

@Data
public class SignIn {
    private String username;
    private String password;
}
