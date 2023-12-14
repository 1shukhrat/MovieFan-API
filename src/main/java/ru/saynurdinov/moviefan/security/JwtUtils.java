package ru.saynurdinov.moviefan.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    //@Value("${security.jwt.secret-key}")
    private final static  SecretKey secret = Jwts.SIG.HS256.key().build();
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
                .signWith(secret)
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
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
