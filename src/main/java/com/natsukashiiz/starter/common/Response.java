package com.natsukashiiz.starter.common;

import com.natsukashiiz.starter.common.ResponseCode;
import com.natsukashiiz.starter.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * {
 *   "code": xxxx,
 *   "result": xxxx,
 *   "records": xxxx
 * }
 */
public class Response {

    /**
     * {
     *   "code": 0,
     *   "result": null,
     *   "records": null
     * }
     */
    public static <E> ResponseEntity<?> success() {
        return success(null);
    }

    /**
     * {
     *   "code": 0,
     *   "result": result,
     *   "records": null
     * }
     */
    public static <E> ResponseEntity<?> success(E result) {
        BaseResponse<?> response = BaseResponse.builder()
                .code(ResponseCode.SUCCESS.getValue())
                .result(result)
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * {
     *   "code": 0,
     *   "result": result,
     *   "records": result.size
     * }
     */
    public static <E> ResponseEntity<?> successList(List<E> result) {
        BaseResponse<?> response = BaseResponse.builder()
                .code(ResponseCode.SUCCESS.getValue())
                .result(result)
                .records((long) result.size())
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * {
     *   "code": code,
     *   "result": null,
     *   "records": null
     * }
     */
    public static ResponseEntity<?> error(ResponseCode code) {
        BaseResponse<?> response = BaseResponse.builder()
                .code(code.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * {
     *   "code": 4100,
     *   "result": null,
     *   "records": null
     * }
     */
    public static ResponseEntity<?> unauthorized() {
        BaseResponse<?> response = BaseResponse.builder()
                .code(ResponseCode.UNKNOWN.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * {
     *   "code": 9999,
     *   "result": null,
     *   "records": null
     * }
     */
    public static ResponseEntity<?> unknown() {
        BaseResponse<?> response = BaseResponse.builder()
                .code(ResponseCode.UNKNOWN.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
