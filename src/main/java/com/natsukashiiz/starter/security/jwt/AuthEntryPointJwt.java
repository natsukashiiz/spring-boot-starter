package com.natsukashiiz.starter.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natsukashiiz.starter.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * {
     * "code": 4100,
     * "result": null,
     * "records": null
     * }
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("AuthEntryPointJwt-[Unauthorized]. error: {}", authException.getLocalizedMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), Response.unauthorized().getBody());
    }
}
