package com.natsukashiiz.starter.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignedHistoryResponse {
    private Long uid;
    private String ipv4;
    private String userAgent;
}
