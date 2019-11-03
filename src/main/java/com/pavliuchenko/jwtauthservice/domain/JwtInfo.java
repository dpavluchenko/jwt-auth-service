package com.pavliuchenko.jwtauthservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
@Builder
public class JwtInfo {

    private String subject;
    private Map<String, Object> claims;

    public static final String NAME_KEY = "name";
    public static final String ROLE_KEY = "role";

    public static JwtInfo fromUser(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(NAME_KEY, user.getFullName());
        claims.put(ROLE_KEY, user.getRole().name());
        return JwtInfo.builder()
                .claims(claims)
                .subject(user.getId())
                .build();
    }


    public User toUser() {
        User user = new User();
        user.setFullName(claims.get(NAME_KEY).toString());
        user.setRole(User.Role.valueOf(claims.get(ROLE_KEY).toString()));
        user.setId(subject);
        return user;
    }
}
