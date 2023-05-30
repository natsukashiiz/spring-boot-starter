package com.natsukashiiz.starter.common;
import java.util.Objects;
import java.util.Optional;

public enum ResponseCode implements StateMapping<Integer> {

    /** success */
    SUCCESS(0),

    /** token expire */
    TOKEN_EXPIRE(4001),

    /** unknown error */
    UNKNOWN(9999),
    ;

    private final Integer state;

    ResponseCode(Integer state) {
        this.state = state;
    }

    public static Optional<ResponseCode> find(Integer code){
        // if code null throw error
        Objects.requireNonNull(code);
        for (ResponseCode value : ResponseCode.values()) {
            if(value.is(code)){
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    @Override
    public Integer getMapping() {
        return this.state;
    }
}

