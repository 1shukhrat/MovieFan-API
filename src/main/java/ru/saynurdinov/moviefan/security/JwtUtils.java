package ru.saynurdinov.moviefan.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${security.jwt.secret-key}")
    private String secret;
    @Value("${security.jwt.expiration-time}")
    private Duration expirationTime;

    public String generateToken(UserDetailsImpl userDetails) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + expirationTime.toMillis());
        return Jwts.builder()
                .claim("id", userDetails.getId())
                .claim("roles", userDetails.getRoles())
                .subject(userDetails.getLogin())
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Long getId(String token) {
        return getClaims(token)
                .get("id", Long.class);
    }

    public String getLogin(String token) {
        return getClaims(token)
                .getSubject();
    }

    public List<String> getRoles(String token) {
        return getClaims(token)
                .get("roles", List.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
