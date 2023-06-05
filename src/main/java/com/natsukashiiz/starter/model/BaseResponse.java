package com.natsukashiiz.starter.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse<E> {
    private Integer code;
    private E result;
    private Long records;

}
