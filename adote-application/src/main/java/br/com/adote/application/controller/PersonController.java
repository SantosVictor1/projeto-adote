package br.com.adote.application.controller;

import br.com.adote.application.api.PersonApi;
import br.com.adote.application.api.request.CreateLegalPersonRequest;
import br.com.adote.application.api.response.LegalPersonResponse;
import br.com.adote.application.dto.LegalPersonDTO;
import br.com.adote.application.mapper.LegalPersonMapper;
import br.com.adote.application.service.ILegalPersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Person")
public class PersonController implements PersonApi {

    private final ILegalPersonService personService;

    public PersonController(ILegalPersonService personService) {
        this.personService = personService;
    }


    @Override
    public LegalPersonResponse create(
            @RequestBody @Validated CreateLegalPersonRequest createLegalPersonRequest
    ) {
        LegalPersonDTO createLegalPersonDTO = LegalPersonMapper.toDto(createLegalPersonRequest);
        LegalPersonDTO legalPersonDTO = personService.create(createLegalPersonDTO);

        return LegalPersonMapper.toResponse(legalPersonDTO);
    }
}
