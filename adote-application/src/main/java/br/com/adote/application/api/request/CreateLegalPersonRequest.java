package br.com.adote.application.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

public record CreateLegalPersonRequest(
    @NotEmpty
    String name,
    @NotNull @Min(value = 18)
    Integer age,
    @NotEmpty @Email
    String email,
    @NotEmpty
    String personType,
    @NotEmpty @CNPJ
    String cnpj,
    @NotEmpty
    String fantasyName,
    @NotEmpty
    String corporateName
) {
}
