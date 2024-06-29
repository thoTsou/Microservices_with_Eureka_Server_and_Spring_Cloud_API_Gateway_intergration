package com.thotsou.user.register.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expirationTime;

    private SecretKey signingKey;

    @PostConstruct
    public void setUpTokenSigningKey() {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateJWT(String userEmail, String JWTType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userEmail);
        claims.put("type", JWTType);

        long expirationTimeLong;
        if ("ACCESS".equals(JWTType)) {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000;
        } else {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000 * 5;
        }
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);

        return Jwts.builder()
                .claims(claims)
                .subject(userEmail)
                .issuedAt(createdDate)
                .expiration(expirationDate)
                .signWith(this.signingKey)
                .compact();
    }

}
