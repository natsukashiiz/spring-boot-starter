package com.natsukashiiz.starter.controller;

import com.natsukashiiz.starter.model.request.LoginRequest;
import com.natsukashiiz.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v2/auth")
@RequiredArgsConstructor
public class AuthV2Api {
    private final UserService service;

    @PostMapping("/signin")
    public ResponseEntity<?> login(HttpServletRequest httpRequest, @RequestBody LoginRequest request) {
        return service.loginV2(request, httpRequest);
    }
}
