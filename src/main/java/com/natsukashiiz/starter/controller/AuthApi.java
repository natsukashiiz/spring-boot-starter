package com.natsukashiiz.starter.controller;

import com.natsukashiiz.starter.model.request.LoginRequest;
import com.natsukashiiz.starter.model.request.RegisterRequest;
import com.natsukashiiz.starter.model.request.TokenRefreshRequest;
import com.natsukashiiz.starter.service.UserService;
import com.natsukashiiz.starter.utils.Comm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final UserService service;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterRequest request) {
        return service.create(request);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(HttpServletRequest httpRequest, @RequestBody LoginRequest request) {
        return service.login(request, httpRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRefreshRequest request) {
        return service.refreshToken(request);
    }

}
