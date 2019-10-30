package com.pavliuchenko.jwtauthservice.service.jwt;

import com.pavliuchenko.jwtauthservice.domain.User;
import com.pavliuchenko.jwtauthservice.service.key.KeyService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService{

    @Value("${jwt.expiration.minutes}")
    private Integer jwtExpiration;

    private final KeyService keyService;

    private static final String JWT_PREFIX = "Bearer ";

    @Override
    public Mono<Jwt> validateToken(String token) {
        return Mono.justOrEmpty(token)
                .filter(t-> t.length() > JWT_PREFIX.length())
                .flatMap(t-> Mono.justOrEmpty(t.substring(JWT_PREFIX.length())))
                .flatMap(t -> Mono.just(Jwts.parser().setSigningKey(keyService.getPublicKey())
                .parse(t)))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<User> getFromToken(Jwt token) {
        token.getBody();
        return null;
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
