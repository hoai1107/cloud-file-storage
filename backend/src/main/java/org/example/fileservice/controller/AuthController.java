package org.example.fileservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.fileservice.dto.request.UserLoginDTO;
import org.example.fileservice.dto.request.UserRegisterDTO;
import org.example.fileservice.dto.response.JwtTokenDTO;
import org.example.fileservice.dto.response.UserDTO;
import org.example.fileservice.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        UserDTO userDTO = authService.register(userRegisterDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        JwtTokenDTO token = authService.login(userLoginDTO);
        ResponseCookie cookie = ResponseCookie.from("access-token", token.jwtToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(token);
    }

    @GetMapping("/test")
    public UUID getUsername(@RequestAttribute("id") UUID id) {
        return id;
    }
}
