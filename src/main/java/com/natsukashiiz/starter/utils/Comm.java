package com.natsukashiiz.starter.utils;

import com.natsukashiiz.starter.model.Pagination;
import com.natsukashiiz.starter.service.UserDetailsImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public static Pageable getPaginate(Pagination paginate) {
        return PageRequest.of(paginate.getPage() > 0 ? paginate.getPage() - 1 : 0, paginate.getLimit(), Sort.Direction.fromString(paginate.getSortType()), paginate.getSortBy());
    }

    public static String getDeviceType(String userAgent) {
        String deviceType;

        String iPhone = "iPhone";
        String Android = "Android";
        String Windows = "Windows";
        String Unknown = "Unknown";

        if (userAgent.contains(iPhone)) {
            deviceType = iPhone;
        } else if (userAgent.contains(Android)) {
            deviceType = Android;
        } else if (userAgent.contains(Windows)) {
            deviceType = Windows;
        } else {
            deviceType = Unknown;
        }

        return deviceType;
    }
}
