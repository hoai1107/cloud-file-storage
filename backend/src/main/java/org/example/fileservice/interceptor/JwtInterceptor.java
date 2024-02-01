package org.example.fileservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.fileservice.exception.BadRequestException;
import org.example.fileservice.service.JwtService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    public JwtInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String getJwtCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("access-token"))
                .findFirst()
                .orElseThrow()
                .getValue();
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        try {
            String id = jwtService.getId(getJwtCookie(request));
            request.setAttribute("id", id);
        } catch (Exception e) {
            throw new BadRequestException("Invalid token");
        }

        return true;
    }
}
