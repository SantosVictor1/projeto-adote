package br.com.adote.application.api;

import br.com.adote.application.api.request.CreateLegalPersonRequest;
import br.com.adote.application.api.response.LegalPersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(value = {"/person"})
public interface PersonApi {

    @PostMapping("/legal")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create legal person",
            description = "creates a legal person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation")
    })
    LegalPersonResponse create(@RequestBody @Validated CreateLegalPersonRequest createLegalPersonRequest);
}
