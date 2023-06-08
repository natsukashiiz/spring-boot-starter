package com.natsukashiiz.starter.model;

import lombok.Builder;
import lombok.Data;

@Data
public class Pagination {
    private Integer page = 0;
    private Integer limit = 10;
    private String sortType = "asc";
    private String sortBy = "id";
}
