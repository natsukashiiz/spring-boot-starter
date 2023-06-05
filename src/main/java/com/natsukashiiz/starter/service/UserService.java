package com.natsukashiiz.starter.service;

import com.natsukashiiz.starter.common.ResponseCode;
import com.natsukashiiz.starter.entity.User;
import com.natsukashiiz.starter.model.Response;
import com.natsukashiiz.starter.model.request.LoginRequest;
import com.natsukashiiz.starter.model.request.RegisterRequest;
import com.natsukashiiz.starter.model.response.TokenResponse;
import com.natsukashiiz.starter.repository.UserRepo;
import com.natsukashiiz.starter.security.jwt.JwtUtils;
import com.natsukashiiz.starter.utils.ValidateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepo repo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

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
        logger.info("Login-[user]. {}", user);

        if (!matchPassword(password, user.getPassword())) {
            logger.error("Login-[block]:(incorrect password)");
            return Response.error(ResponseCode.INVALID_USERNAME_PASSWORD);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        logger.info("Login-[authenticationToken]. {}", authenticationToken.getPrincipal());
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        logger.info("Login-[authentication]. {}", authentication.getPrincipal());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        TokenResponse response = jwtUtils.generateToken(authenticationToken);
        System.out.println("response = " + response);
        return Response.success(response);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
