package br.com.adote.application.controller;

import br.com.adote.application.api.PetApi;
import br.com.adote.application.api.request.CreatePetRequest;
import br.com.adote.application.api.response.PetImageResponse;
import br.com.adote.application.api.response.PetResponse;
import br.com.adote.application.dto.PetDTO;
import br.com.adote.application.dto.PetImageDTO;
import br.com.adote.application.mapper.PetImageMapper;
import br.com.adote.application.mapper.PetMapper;
import br.com.adote.application.service.IPetService;
import br.com.adote.application.service.IS3Service;
import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "Pet")
public class PetController implements PetApi {
    private final IPetService petService;

    public PetController(IPetService petService) {
        this.petService = petService;
    }

    @Override
    public List<PetResponse> create(
        @RequestBody @Validated List<CreatePetRequest> createPetRequestList,
        @PathVariable(value = "legalPersonId") String legalPersonId
    ) {
        List<PetDTO> petDTOList = createPetRequestList.stream().map(
            PetMapper::toDto
        ).toList();

        petDTOList = petService.batchCreate(petDTOList, legalPersonId);

        return petDTOList.stream().map(petDTO -> PetMapper.toResponse(petDTO, legalPersonId)).toList();
    }

    @Override
    public List<PetImageResponse> uploadPetImage(
        @RequestPart("images") List<MultipartFile> images,
        @PathVariable(value = "id") String id,
        @PathVariable(value = "legalPersonId") String legalPersonId
    ) throws IOException {
        List<PetImageDTO> petImageDTOList = petService.uploadImages(id, legalPersonId, images);

        return petImageDTOList.stream().map(petImageDTO ->
            PetImageMapper.toResponse(petImageDTO, id, legalPersonId)).toList();
    }
}
