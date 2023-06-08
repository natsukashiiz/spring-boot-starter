package com.natsukashiiz.starter.model;

import com.natsukashiiz.starter.model.response.PaginationResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
public class BaseResponse<E> {
    private Integer code;
    private E result;
    private PaginationResponse pagination;
}
