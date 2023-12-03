package br.com.adote.application.api.response;

import br.com.adote.application.enums.LegalPersonType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LegalPersonResponse(
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
) {
}
