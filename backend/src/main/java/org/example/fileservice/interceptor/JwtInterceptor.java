package org.example.fileservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.fileservice.exception.BadRequestException;
import org.example.fileservice.service.JwtService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    public JwtInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        try {
            String id = jwtService.getId(request.getHeader("Authorization").substring(7));
            request.setAttribute("id", id);
        } catch (Exception e) {
            throw new BadRequestException("Invalid token");
        }

        return true;
    }
}
