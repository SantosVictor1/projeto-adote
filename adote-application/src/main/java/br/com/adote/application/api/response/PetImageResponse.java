package br.com.adote.application.api.response;

import java.time.OffsetDateTime;

public record PetImageResponse(
    Long id,
    String petId,
    String legalPersonId,
    String s3Url,
    String fileName,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
