package com.natsukashiiz.starter.controller;

import com.natsukashiiz.starter.model.Pagination;
import com.natsukashiiz.starter.model.request.ChangePasswordRequest;
import com.natsukashiiz.starter.model.request.UpdateUserRequest;
import com.natsukashiiz.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserApi {
    private final UserService service;

    @GetMapping
    public ResponseEntity<?> get() {
        return service.getMe();
    }

    @GetMapping("/signedHistory")
    public ResponseEntity<?> signedHistory(Pagination pagination) {
        return service.signedHistory(pagination);
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody UpdateUserRequest request) {
        return service.update(request);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        return service.changePassword(request);
    }
}
