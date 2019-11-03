package com.pavliuchenko.jwtauthservice.service.jwt;

import com.pavliuchenko.jwtauthservice.domain.JwtInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

public class CustomJwtHandler extends JwtHandlerAdapter<JwtInfo> {

    @Override
    public JwtInfo onClaimsJws(Jws<Claims> jws) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtInfo.NAME_KEY, jws.getBody().get(JwtInfo.NAME_KEY));
        claims.put(JwtInfo.ROLE_KEY, jws.getBody().get(JwtInfo.ROLE_KEY));
        return JwtInfo.builder()
                .claims(claims)
                .subject(jws.getBody().getSubject())
                .build();
    }
}
