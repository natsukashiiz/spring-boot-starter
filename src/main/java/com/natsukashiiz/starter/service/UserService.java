package com.natsukashiiz.starter.service;

import com.natsukashiiz.starter.common.Response;
import com.natsukashiiz.starter.common.ResponseCode;
import com.natsukashiiz.starter.entity.SignedHistory;
import com.natsukashiiz.starter.entity.User;
import com.natsukashiiz.starter.model.Pagination;
import com.natsukashiiz.starter.model.request.*;
import com.natsukashiiz.starter.model.response.TokenResponse;
import com.natsukashiiz.starter.model.response.UserResponse;
import com.natsukashiiz.starter.repository.SignedHistoryRepo;
import com.natsukashiiz.starter.repository.UserRepo;
import com.natsukashiiz.starter.security.jwt.JwtResfreshUtils;
import com.natsukashiiz.starter.security.jwt.JwtUtils;
import com.natsukashiiz.starter.utils.Comm;
import com.natsukashiiz.starter.utils.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepo repo;
    private final SignedHistoryRepo historyRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final JwtResfreshUtils jwtResfreshUtils;

    public ResponseEntity<?> getMe(UserDetailsImpl auth) {
        Optional<User> opt = repo.findByUsername(auth.getUsername());
        if (!opt.isPresent()) {
            return Response.error(ResponseCode.NOT_FOUND);
        }

        User user = opt.get();
        UserResponse response = buildResponse(user);
        return Response.success(response);
    }

    public ResponseEntity<?> signedHistory(UserDetailsImpl auth, Pagination paginate) {
        Pageable pageable = Comm.getPaginate(paginate);
        Page<SignedHistory> histories = historyRepo.findByUid(auth.getId(), pageable);
        return Response.successList(histories);
    }


    public ResponseEntity<?> update(UserDetailsImpl auth, UpdateUserRequest request) {
        if (ValidateUtil.invalidEmail(request.getEmail())) {
            return Response.error(ResponseCode.INVALID_EMAIL);
        }
        Optional<User> opt = repo.findByUsername(auth.getUsername());
        if (!opt.isPresent()) {
            return Response.error(ResponseCode.NOT_FOUND);
        }

        User user = opt.get();
        user.setEmail(request.getEmail());
        User save = repo.save(user);
        UserResponse response = buildResponse(save);
        return Response.success(response);
    }

    public ResponseEntity<?> changePassword(UserDetailsImpl auth, ChangePasswordRequest request) {
        if (ValidateUtil.invalidPassword(request.getCurrentPassword())) {
            return Response.error(ResponseCode.INVALID_PASSWORD);
        }

        if (ValidateUtil.invalidPassword(request.getNewPassword())) {
            return Response.error(ResponseCode.INVALID_NEW_PASSWORD);
        }

        if (!Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            return Response.error(ResponseCode.PASSWORD_NOT_MATCH);
        }

        Optional<User> opt = repo.findByUsername(auth.getUsername());
        if (!opt.isPresent()) {
            return Response.error(ResponseCode.NOT_FOUND);
        }

        User user = opt.get();

        // check password
        if (!matchPassword(request.getCurrentPassword(), user.getPassword())) {
            return Response.error(ResponseCode.INVALID_PASSWORD);
        }

        // password encoded
        String passwordEncoded = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(passwordEncoded);
        User save = repo.save(user);
        UserResponse response = buildResponse(save);
        return Response.success(response);
    }

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
        UserResponse response = buildResponse(save);
        return Response.success(response);
    }

    public ResponseEntity<?> login(LoginRequest request, HttpServletRequest httpRequest) {

        String ipv4 = Comm.getIpAddress(httpRequest);
        String userAgent = Comm.getUserAgent(httpRequest);
        String device = Comm.getDeviceType(userAgent);

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

        // save signed history
        SignedHistory history = new SignedHistory();
        history.setUid(user.getId());
        history.setIpv4(ipv4);
        history.setDevice(device);
        history.setUserAgent(userAgent);
        historyRepo.save(history);

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

    public UserResponse buildResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
