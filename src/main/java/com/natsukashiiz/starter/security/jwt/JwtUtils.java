package com.natsukashiiz.starter.security.jwt;

import com.natsukashiiz.starter.model.response.TokenResponse;
import com.natsukashiiz.starter.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${natsukashiiz.jwt.issuer}")
    private String issuer;
    @Value("${natsukashiiz.jwt.secret}")
    private String secret;
    @Value("${natsukashiiz.jwt.expirationMs}")
    private Long expirationMs;

    public String generate(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public TokenResponse generateToken(Authentication authentication) {
        Date expiration = new Date(new Date().getTime() + expirationMs);

        String token = Jwts.builder()
                .setIssuer(issuer)
                .setSubject(authentication.getPrincipal().toString())
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
        return TokenResponse.builder()
                .token(token)
                .expire(expiration.getTime())
                .build();
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
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
