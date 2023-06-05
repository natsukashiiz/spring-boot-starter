package com.natsukashiiz.starter.model.request;

import lombok.Data;

/**
 * {
 *     "email": "xxxx"
 * }
 */
@Data
public class UpdateUserRequest {
    private String email;
}
