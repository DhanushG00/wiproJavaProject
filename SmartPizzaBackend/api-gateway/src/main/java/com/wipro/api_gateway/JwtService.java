package com.wipro.api_gateway;

import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    private final String SECRET = "smartpizza_secret_key_12345678901234567890";

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("roles", String.class);
    }
    public Integer extractId(String token) {
        return extractAllClaims(token).get("userId", Integer.class);
    }

    public boolean validateToken(String token) {
        return !extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }
}
