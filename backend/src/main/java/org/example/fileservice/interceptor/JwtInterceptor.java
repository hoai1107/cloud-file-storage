package org.example.fileservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.fileservice.exception.BadRequestException;
import org.example.fileservice.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    private Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);


    public JwtInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        try {
            String id = jwtService.getId(request.getHeader("Authorization").substring(7));
            request.setAttribute("id", id);
        } catch (Exception e) {
            throw new BadRequestException("Invalid token");
        }

        return true;
    }
}
