package com.natsukashiiz.starter.model;

import lombok.Data;

@Data
public class Pagination {
    private Integer page = 1;
    private Integer limit = 10;
}
