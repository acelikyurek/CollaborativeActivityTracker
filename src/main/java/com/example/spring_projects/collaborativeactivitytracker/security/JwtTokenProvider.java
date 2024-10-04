package com.example.spring_projects.collaborativeactivitytracker.security;

import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.expiration-time}")
    private Long EXPIRES_IN;

    public String generateToken(String email) {
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRES_IN))
                .signWith(getSignInKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    public String getEmailFromToken(String tokenString) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(tokenString)
                .getBody();
        return claims.getSubject();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String tokenString) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(tokenString)
                    .getBody()
                    .getExpiration();
            return true;
        } catch (SignatureException e) {
            System.err.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}