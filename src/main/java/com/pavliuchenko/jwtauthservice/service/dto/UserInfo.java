package com.pavliuchenko.jwtauthservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
    private String id;
    private String fullName;
    private String role;
}
