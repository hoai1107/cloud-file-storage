package org.example.fileservice.dto.request;

public record UserRegisterDTO (
        String username,
        String password,
        String firstName,
        String lastName
) {
}
