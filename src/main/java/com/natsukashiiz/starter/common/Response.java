package com.natsukashiiz.starter.common;

import com.natsukashiiz.starter.model.BaseResponse;
import com.natsukashiiz.starter.model.response.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * {
 * "code": xxxx,
 * "result": xxxx,
 * "records": xxxx
 * }
 */
public class Response {

    /**
     * {
     * "code": 0,
     * "result": null,
     * "records": null
     * }
     */
    public static <E> ResponseEntity<?> success() {
        return success(null);
    }

    /**
     * {
     * "code": 0,
     * "result": result,
     * "records": null
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
     * "code": 0,
     * "result": result,
     * "records": size,
     * "pagination": {
     * "current": xx,
     * "limit": xx,
     * "records": xx,
     * "pages": xx
     * }
     * }
     */
    public static <T> ResponseEntity<?> successList(Page<T> result) {
        PaginationResponse pagination = PaginationResponse.builder()
                .limit(result.getPageable().getPageSize())
                .current(result.getPageable().getPageNumber() + 1)
                .records((int) result.getTotalElements())
                .pages(result.getTotalPages())
                .first(result.isFirst())
                .last(result.isLast())
                .build();
        BaseResponse<?> response = BaseResponse.builder()
                .code(ResponseCode.SUCCESS.getValue())
                .result(result.getContent())
                .pagination(pagination)
                .build();
        return ResponseEntity.ok(response);
    }

    /**
     * {
     * "code": code,
     * "result": null,
     * "records": null
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
     * "code": 4100,
     * "result": null,
     * "records": null
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
     * "code": 9999,
     * "result": null,
     * "records": null
     * }
     */
    public static ResponseEntity<?> unknown() {
        BaseResponse<?> response = BaseResponse.builder()
                .code(ResponseCode.UNKNOWN.getValue())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
