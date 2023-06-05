package com.natsukashiiz.starter.model.response;

import lombok.Builder;
import lombok.Data;

/**
 * {
 *     "id": xxxx,
 *     "email": "xxxx",
 *     "username": "xxxx"
 * }
 */
@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String username;
}
