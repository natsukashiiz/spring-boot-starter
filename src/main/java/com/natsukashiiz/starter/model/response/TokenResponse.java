package com.natsukashiiz.starter.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * {
 *     "refreshToken": "xxxx",
 *     "accessToken": "xxxx",
 *     "refreshExpire": xxxx,
 *     "accessExpire": xxxx
 * }
 */
@Data
@Builder
public class TokenResponse {
    private String refreshToken;
    private String accessToken;
    private Long refreshExpire;
    private Long accessExpire;
}
