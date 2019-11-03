package com.pavliuchenko.jwtauthservice.service.jwt;

import com.pavliuchenko.jwtauthservice.domain.JwtInfo;
import com.pavliuchenko.jwtauthservice.domain.User;
import com.pavliuchenko.jwtauthservice.service.key.KeyService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService{

    @Value("${jwt.expiration.minutes}")
    private Integer jwtExpiration;

    private final KeyService keyService;

    private static final String ISSUER = "auth-service";
    private static final String JWT_PREFIX = "Bearer ";

    @Override
    public Mono<JwtInfo> validateToken(String token) {
        return Mono.justOrEmpty(token)
                .filter(t-> t.length() > JWT_PREFIX.length())
                .flatMap(t-> Mono.justOrEmpty(t.substring(JWT_PREFIX.length())))
                .flatMap(t -> Mono.just(Jwts.parser().setSigningKey(keyService.getPublicKey())
                .parse(t, new CustomJwtHandler())))
                .onErrorResume(error -> Mono.empty());
    }


    @Override
    public String createTokenForUser(User user) {
        JwtInfo jwtInfo = JwtInfo.fromUser(user);
        return Jwts.builder()
                .setHeaderParam("alg", SignatureAlgorithm.RS256.getValue())
                .setIssuer(ISSUER)
                .setSubject(jwtInfo.getSubject())
                .setClaims(jwtInfo.getClaims())
                .setExpiration(DateUtils.addMinutes(new Date(), jwtExpiration))
                .signWith(keyService.getPrivateKey())
                .compact();
    }
}
