package com.natsukashiiz.starter.model.request;

import lombok.Builder;
import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
