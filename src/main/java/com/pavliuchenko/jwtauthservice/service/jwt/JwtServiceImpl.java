package com.pavliuchenko.jwtauthservice.service.jwt;

import com.pavliuchenko.jwtauthservice.domain.User;
import com.pavliuchenko.jwtauthservice.service.key.KeyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService{

    @Value("${jwt.expiration.minutes}")
    private Integer jwtExpiration;

    private final KeyService keyService;

    @Override
    public boolean validateToken(String token) {
        return false;
    }

    @Override
    public String createTokenForUser(User user) {
        Map<String, Object> customClaims = new HashMap<>();
        customClaims.put("name", user.getFullName());
        customClaims.put("role", user.getRole().name());
        return Jwts.builder()
                .setHeaderParam("alg", SignatureAlgorithm.RS256.getValue())
                .setIssuer("auth-service")
                .setSubject(user.getId())
                .setClaims(customClaims)
                .setExpiration(DateUtils.addMinutes(new Date(), jwtExpiration))
                .signWith(keyService.getPublicKey())
                .compact();
    }
}
