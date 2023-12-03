package br.com.adote.application.dto;

import java.time.OffsetDateTime;

public record PetImageDTO(
    Long id,
    String s3Url,
    String fileName,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
