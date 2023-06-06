package com.natsukashiiz.starter.utils;

import com.natsukashiiz.starter.service.UserDetailsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

public class Comm {
    public static String getUsernameFromAuth() {
        Authentication authentication = getAuth();
        return authentication.getName();
    }

    public static UserDetailsImpl getUserAuth() {
        return (UserDetailsImpl) getAuth().getPrincipal();
    }

    public static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.USER_AGENT);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}
