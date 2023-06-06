package com.natsukashiiz.starter.controller;

import com.natsukashiiz.starter.common.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/global")
public class GlobalApi {
    @GetMapping
    public ResponseEntity<?> get() {
        return Response.success();
    }
}
