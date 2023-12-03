package br.com.adote.application.dto;

import br.com.adote.application.enums.PetSize;
import br.com.adote.application.enums.PetType;
import java.time.OffsetDateTime;

public record PetDTO(
    String id,
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
