package com.natsukashiiz.starter.controller;

import com.natsukashiiz.starter.common.Response;
import com.natsukashiiz.starter.service.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/private")
public class PrivateApi {
    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal UserDetailsImpl auth) {
        return Response.success(auth);
    }
}
