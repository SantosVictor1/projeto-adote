package br.com.adote.application.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreatePetRequest(
    @NotEmpty
    String name,
    @NotNull
    Integer age,
    @NotNull
    Boolean isVaccinated,
    @NotEmpty
    String type,
    @NotEmpty
    String size,
    @NotEmpty
    String description
) {
}
