package org.example.fileservice.dto.response;

import java.time.Instant;

public record S3FileDTO(
        String id,
        String name,
        String type,
        Long size,
        Instant lastModified
        ) {
}
