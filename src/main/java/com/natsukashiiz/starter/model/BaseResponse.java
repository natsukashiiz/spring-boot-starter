package com.natsukashiiz.starter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.natsukashiiz.starter.common.ResponseCode;
import com.natsukashiiz.starter.model.response.PaginationResponse;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<E> {
    private Integer code;
    @Enumerated(value = EnumType.STRING)
    private ResponseCode text;
    private E result;
    private PaginationResponse pagination;
}
