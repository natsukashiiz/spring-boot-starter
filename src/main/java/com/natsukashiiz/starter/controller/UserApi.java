package com.natsukashiiz.starter.controller;

import com.natsukashiiz.starter.model.Pagination;
import com.natsukashiiz.starter.model.request.ChangePasswordRequest;
import com.natsukashiiz.starter.model.request.UpdateUserRequest;
import com.natsukashiiz.starter.service.UserDetailsImpl;
import com.natsukashiiz.starter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserApi {
    private final UserService service;

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal UserDetailsImpl auth) {
        return service.getMe(auth);
    }

    /**
     * {baseUrl}/signedHistory?page=1&limit=3&sortBy=cdt&sortType=desc
     */
    @GetMapping("/signedHistory")
    public ResponseEntity<?> signedHistory(@AuthenticationPrincipal UserDetailsImpl auth, Pagination pagination) {
        return service.signedHistory(auth, pagination);
    }

    @PatchMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal UserDetailsImpl auth, @RequestBody UpdateUserRequest request) {
        return service.update(auth, request);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserDetailsImpl auth, @RequestBody ChangePasswordRequest request) {
        return service.changePassword(auth, request);
    }
}
