package com.natsukashiiz.starter.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String refreshToken;
    private String accessToken;
    private Long refreshExpire;
    private Long accessExpire;
}
