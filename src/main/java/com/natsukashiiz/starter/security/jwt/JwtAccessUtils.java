package com.natsukashiiz.starter.security.jwt;

import com.natsukashiiz.starter.entity.User;
import com.natsukashiiz.starter.model.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtAccessUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${natsukashiiz.jwt.issuer}")
    private String issuer;
    @Value("${natsukashiiz.jwt.access.secret}")
    private String secret;
    @Value("${natsukashiiz.jwt.access.expirationMs}")
    private Long expirationMs;

    public Token generateToken(User user) {
        Date expiration = new Date(new Date().getTime() + expirationMs);
        Claims claims = Jwts.claims().setId(UUID.randomUUID().toString());
        claims.put("uid", user.getId());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
        return Token.builder()
                .token(token)
                .expire(expiration.getTime())
                .build();
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().get("username").toString();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
