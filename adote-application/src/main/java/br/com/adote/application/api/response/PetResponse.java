package br.com.adote.application.api.response;

import br.com.adote.application.enums.PetSize;
import br.com.adote.application.enums.PetType;
import java.time.OffsetDateTime;

public record PetResponse(
    String id,
    String legalPersonId,
    String name,
    int age,
    Boolean isVaccinated,
    PetType type,
    PetSize size,
    String description,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}
