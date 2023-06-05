package com.natsukashiiz.starter.security.jwt;

import com.natsukashiiz.starter.entity.User;
import com.natsukashiiz.starter.model.Token;
import com.natsukashiiz.starter.model.response.TokenResponse;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtAccessUtils jwtAccessUtils;
    private final JwtResfreshUtils jwtResfreshUtils;

    public TokenResponse generate(User user) {
        Token access = jwtAccessUtils.generateToken(user);
        Token refresh = jwtResfreshUtils.generateToken(user);

        return TokenResponse.builder()
                .accessToken(access.getToken())
                .accessExpire(access.getExpire())
                .refreshToken(refresh.getToken())
                .refreshExpire(refresh.getExpire())
                .build();
    }
}
