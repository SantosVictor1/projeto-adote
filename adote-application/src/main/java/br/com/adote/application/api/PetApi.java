package br.com.adote.application.api;

import br.com.adote.application.api.request.CreatePetRequest;
import br.com.adote.application.api.response.PetImageResponse;
import br.com.adote.application.api.response.PetResponse;
import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = {"/pet"})
public interface PetApi {

    @PostMapping("/batch/{legalPersonId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create pets",
            description = "creates a list or one pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation")
    })
    List<PetResponse> create(
        @RequestBody @Validated List<CreatePetRequest> createPetRequestList,
        @PathVariable(value = "legalPersonId") String legalPersonId
    );

    @PostMapping(value = "/{id}/upload/{legalPersonId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Saves pets images",
            description = "creates a list or one pet images")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    List<PetImageResponse> uploadPetImage(
        @RequestPart("images") List<MultipartFile> images,
        @PathVariable(value = "id") String id,
        @PathVariable(value = "legalPersonId") String legalPersonId
    ) throws IOException;
}
