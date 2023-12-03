package br.com.adote.application.dto;

import br.com.adote.application.enums.LegalPersonType;
import java.time.OffsetDateTime;

public record LegalPersonDTO(
    String id,
    String name,
    int age,
    String email,
    LegalPersonType personType,
    String cnpj,
    String fantasyName,
    String corporateName,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
){}