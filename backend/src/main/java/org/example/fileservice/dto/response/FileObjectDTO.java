package org.example.fileservice.dto.response;

import java.time.Instant;

public record FileObjectDTO (String key, long size, Instant lastModified) {
}
