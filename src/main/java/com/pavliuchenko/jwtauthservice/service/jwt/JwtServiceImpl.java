package com.pavliuchenko.jwtauthservice.service.jwt;

import com.pavliuchenko.jwtauthservice.service.key.KeyService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService{

    private final KeyService keyService;

    @Override
    public boolean isValidToken(String token) {
        return false;
    }

    @Override
    public String createToken() {
        return Jwts.builder()
                .setHeaderParam("alg", SignatureAlgorithm.RS256.getValue())
                .setIssuer("auth-service")
                .setSubject("").compact();
    }
}
