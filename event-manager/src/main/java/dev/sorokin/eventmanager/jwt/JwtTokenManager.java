package dev.sorokin.eventmanager.jwt;

import dev.sorokin.eventmanager.model.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenManager {

    private final SecretKey secretKey;
    private final long expirationTime;

    public JwtTokenManager(
            @Value("${jwt.secret-key}") String keyString,
            @Value("${jwt.lifetime}") long expirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(keyString.getBytes());
        this.expirationTime = expirationTime;
    }

    public String generateToken(String login, UserRole role, Long userId) {
        return Jwts.builder()
                .subject(login)
                .claim("role", role.name())
                .claim("userId", userId)
                .signWith(secretKey)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .compact();
    }

    public String getLoginFromToken(String jwt) {
        return parseClaims(jwt).getSubject();
    }

    public String getRoleFromToken(String jwt) {
        return parseClaims(jwt).get("role", String.class);
    }

    private Claims parseClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

}