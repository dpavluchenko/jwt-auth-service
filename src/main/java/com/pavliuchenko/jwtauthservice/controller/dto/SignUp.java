package com.pavliuchenko.jwtauthservice.controller.dto;

import lombok.Data;

@Data
public class SignUp {
    private String username;
    private String password;
    private String fullName;
}
