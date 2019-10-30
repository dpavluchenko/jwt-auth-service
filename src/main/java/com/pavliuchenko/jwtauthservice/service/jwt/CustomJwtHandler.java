package com.pavliuchenko.jwtauthservice.service.jwt;

import com.pavliuchenko.jwtauthservice.domain.JwtInfo;
import io.jsonwebtoken.*;

public class CustomJwtHandler extends JwtHandlerAdapter<JwtInfo> {

    @Override
    public JwtInfo onClaimsJws(Jws<Claims> jws) {
        //TODO implement converting logic
    }
}
