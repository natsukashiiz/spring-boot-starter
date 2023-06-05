package com.natsukashiiz.starter.service;

import com.natsukashiiz.starter.common.ResponseCode;
import com.natsukashiiz.starter.entity.User;
import com.natsukashiiz.starter.model.Response;
import com.natsukashiiz.starter.model.request.LoginRequest;
import com.natsukashiiz.starter.model.request.RegisterRequest;
import com.natsukashiiz.starter.model.request.TokenRefreshRequest;
import com.natsukashiiz.starter.model.response.TokenResponse;
import com.natsukashiiz.starter.repository.UserRepo;
import com.natsukashiiz.starter.security.jwt.JwtResfreshUtils;
import com.natsukashiiz.starter.security.jwt.JwtUtils;
import com.natsukashiiz.starter.utils.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final JwtResfreshUtils jwtResfreshUtils;

    public ResponseEntity<?> create(RegisterRequest request) {
        // validate
        if (ValidateUtil.invalidEmail(request.getEmail())) {
            logger.error("Create-[block]:(validate email)");
            return Response.error(ResponseCode.INVALID_EMAIL);
        }

        if (ValidateUtil.invalidUsername(request.getUsername())) {
            logger.error("Create-[block]:(validate username)");
            return Response.error(ResponseCode.INVALID_USERNAME);
        }

        if (ValidateUtil.invalidPassword(request.getPassword())) {
            logger.error("Create-[block]:(validate password)");
            return Response.error(ResponseCode.INVALID_PASSWORD);
        }

        String email = request.getEmail();
        String username = request.getUsername();
        String password = request.getPassword();

        // check email existed
        if (repo.existsByEmail(email)) {
            return Response.error(ResponseCode.EXISTED_EMAIL);
        }

        // check username existed
        if (repo.existsByUsername(username)) {
            return Response.error(ResponseCode.EXISTED_USERNAME);
        }

        // encode password
        String passwordEncoded = passwordEncoder.encode(password);

        // to entity
        User entity = new User();
        entity.setEmail(email);
        entity.setUsername(username);
        entity.setPassword(passwordEncoded);

        // save
        User save = repo.save(entity);
        return Response.success(save);
    }

    public ResponseEntity<?> login(LoginRequest request) {
        // validate
        if (ValidateUtil.invalidUsername(request.getUsername())) {
            logger.error("Login-[block]:(validate username)");
            return Response.error(ResponseCode.INVALID_USERNAME);
        }

        if (ValidateUtil.invalidPassword(request.getPassword())) {
            logger.error("Login-[block]:(validate password)");
            return Response.error(ResponseCode.INVALID_PASSWORD);
        }

        String username = request.getUsername();
        String password = request.getPassword();

        // check user in db
        Optional<User> opt = repo.findByUsername(username);

        if (!opt.isPresent()) {
            logger.error("Login-[block]:(not found)");
            return Response.error(ResponseCode.INVALID_USERNAME_PASSWORD);
        }

        User user = opt.get();
        if (!matchPassword(password, user.getPassword())) {
            logger.error("Login-[block]:(incorrect password)");
            return Response.error(ResponseCode.INVALID_USERNAME_PASSWORD);
        }

        TokenResponse token = token(user);
        return Response.success(token);
    }

    public ResponseEntity<?> refreshToken(TokenRefreshRequest request) {
        if (Objects.isNull(request.getRefreshToken())) {
            return Response.error(ResponseCode.INVALID_REQUEST);
        }

        String refreshToken = request.getRefreshToken();
        if (!jwtResfreshUtils.validateJwtToken(refreshToken)) {
            return Response.error(ResponseCode.REFRESH_TOKEN_EXPIRE);
        }

        String username = jwtResfreshUtils.getUsernameFromToken(refreshToken);
        Optional<User> opt = repo.findByUsername(username);
        if (!opt.isPresent()) {
            return Response.error(ResponseCode.UNAUTHORIZED);
        }
        User user = opt.get();
        TokenResponse token = token(user);
        return Response.success(token);
    }

    public TokenResponse token(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return jwtUtils.generate(user);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
