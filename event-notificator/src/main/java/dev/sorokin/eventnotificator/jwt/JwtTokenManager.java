package dev.sorokin.eventnotificator.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

@Component
public class JwtTokenManager {

    private final SecretKey secretKey;

    public JwtTokenManager(
            @Value("${jwt.secret-key}") String keyString
    ) {
        this.secretKey = Keys.hmacShaKeyFor(keyString.getBytes());
    }

    public String getRoleFromToken(String jwt) {
        return parseClaims(jwt).get("role", String.class);
    }

    public Long getUserIdFromToken(String jwt) {
        return parseClaims(jwt).get("userId", Long.class);
    }

    private Claims parseClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

}