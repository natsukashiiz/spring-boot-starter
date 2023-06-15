package com.natsukashiiz.starter.security.jwt;

import com.natsukashiiz.starter.model.Token;
import com.natsukashiiz.starter.model.response.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
@Slf4j
public class TokenService {
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder decoder) {
        this.encoder = jwtEncoder;
        this.decoder = decoder;
    }

    public TokenResponse generateToken(Authentication authentication) {
        Token accessToken = generateAccessToken(authentication);
        Token refreshToken = generateRefreshToken(authentication);
        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessExpire(accessToken.getExpire())
                .refreshToken(refreshToken.getToken())
                .refreshExpire(refreshToken.getExpire())
                .build();
    }

    public Token generateAccessToken(Authentication authentication) {
        return generateToken(authentication, 60_000);
    }

    public Token generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, 60_000 * 2);
    }

    public Token generateToken(Authentication authentication, long expire) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(expire);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(authentication.getName())
                .build();
        return Token.builder()
                .token(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue())
                .expire(expiresAt.toEpochMilli())
                .build();
    }

    public boolean validate(String token) {
        try {
            decoder.decode(token);
            return true;
        } catch (Exception e) {
            log.error("TokenService-[validate](invalid token). token: {}, error: {}", token, e.getMessage());
            return false;
        }
    }

    public String getUsername(String token) {
        Jwt decode = decoder.decode(token);
        return decode.getSubject();
    }
}
