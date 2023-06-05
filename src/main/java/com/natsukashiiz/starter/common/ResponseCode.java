package com.natsukashiiz.starter.common;

import java.util.Optional;

public enum ResponseCode {
    SUCCESS(0),
    INVALID_REQUEST(4010),
    INVALID_EMAIL(4011),
    INVALID_USERNAME(4012),
    INVALID_PASSWORD(4013),
    INVALID_CODE(4014),
    INVALID_UID(4020),
    INVALID_USERNAME_PASSWORD(4021),
    EXISTED_EMAIL(4031),
    EXISTED_USERNAME(4032),
    NOT_FOUND(4040),
    TOKEN_EXPIRE(4070),
    REFRESH_TOKEN_EXPIRE(4071),
    UNAUTHORIZED(8888),
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

