package com.natsukashiiz.starter.common;

import java.util.Optional;

public enum ResponseCode {
    SUCCESS(0),
    INVALID_REQUEST(4010),
    INVALID_UID(4011),
    INVALID_PASSWORD(4013),
    INVALID_USERNAME_PASSWORD(4012),
    INVALID_CODE(4013),
    NOT_FOUND(4040),
    TOKEN_EXPIRE(4070),
    REFRESH_TOKEN_EXPIRE(4071),
    UNKNOWN(9999);

    private final Integer value;

    ResponseCode(final Integer code) {
        this.value = code;
    }

    public Integer getValue() {
        return value;
    }

    public static Optional<ResponseCode> find(Integer code) {
        for (ResponseCode values : ResponseCode.values()) {
            if (values.value.equals(code))
                return Optional.of(values);
        }
        return Optional.empty();
    }
}

