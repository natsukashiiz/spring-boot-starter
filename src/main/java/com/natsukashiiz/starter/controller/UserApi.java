package com.natsukashiiz.starter.controller;

import com.natsukashiiz.starter.model.request.UpdateUserRequest;
import com.natsukashiiz.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserApi {
    private final UserService service;

    @GetMapping
    public ResponseEntity<?> get() {
        return service.getMe();
    }

    @PostMapping
    public ResponseEntity<?> update(@RequestBody UpdateUserRequest request) {
        return service.update(request);
    }
}
