package com.natsukashiiz.starter.model;

import com.natsukashiiz.starter.common.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Response {

    public static <E> ResponseEntity<?> success() {
        return success(null);
    }


    public static <E> ResponseEntity<?> success(E result) {
        BaseResponse<?> response = BaseResponse.builder()
                .code(ResponseCode.SUCCESS.getValue())
                .result(result)
                .build();
        return ResponseEntity.ok(response);
    }

    public static <E> ResponseEntity<?> successList(List<E> result) {
        BaseResponse<?> response = BaseResponse.builder()
                .code(ResponseCode.SUCCESS.getValue())
                .result(result)
                .count((long) result.size())
                .build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<?> unknown() {
        BaseResponse<?> response = BaseResponse.builder()
                .code(ResponseCode.UNKNOWN.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
