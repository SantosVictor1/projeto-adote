package br.com.adote.application.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(
    String code,
    String message,
    Map<String, List<String>> fields,
    String originalError
) {
}
