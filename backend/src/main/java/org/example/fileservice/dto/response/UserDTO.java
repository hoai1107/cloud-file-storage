package org.example.fileservice.dto.response;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String username,
        String firstName,
        String lastName
) {
}
