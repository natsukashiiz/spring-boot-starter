package com.natsukashiiz.starter.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {
    private String token;
    private Long expire;
}
