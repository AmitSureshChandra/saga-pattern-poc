package com.github.AmitSurechChandra.orderservice.service;

import com.github.AmitSureshChandra.commonmodule.exception.UnauthenticatedException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String fetchUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("sub", String.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthenticatedException();
        }
    }
}
