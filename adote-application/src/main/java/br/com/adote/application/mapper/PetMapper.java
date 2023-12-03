package br.com.adote.application.mapper;

import br.com.adote.application.api.request.CreatePetRequest;
import br.com.adote.application.api.response.PetResponse;
import br.com.adote.application.domain.pet.Pet;
import br.com.adote.application.dto.LegalPersonDTO;
import br.com.adote.application.dto.PetDTO;
import br.com.adote.application.enums.PetSize;
import br.com.adote.application.enums.PetType;

public class PetMapper {

    public static Pet toEntity(PetDTO petDTO, LegalPersonDTO legalPersonDTO) {
        return new Pet(
            petDTO.id(),
            petDTO.name(),
            petDTO.age(),
            petDTO.isVaccinated(),
            petDTO.type(),
            petDTO.size(),
            petDTO.description(),
            LegalPersonMapper.toEntity(legalPersonDTO),
            null,
            petDTO.createdAt(),
            petDTO.updatedAt()
        );
    }

    public static PetDTO toDto(Pet pet) {
        return new PetDTO(
            pet.getId(),
            pet.getName(),
            pet.getAge(),
            pet.getIsVaccinated(),
            pet.getType(),
            pet.getSize(),
            pet.getDescription(),
            pet.getCreatedAt(),
            pet.getUpdatedAt()
        );
    }

    public static PetDTO toDto(CreatePetRequest createPetRequest) {
        return new PetDTO(
            null,
            createPetRequest.name(),
            createPetRequest.age(),
            createPetRequest.isVaccinated(),
            PetType.valueOf(createPetRequest.type()),
            PetSize.valueOf(createPetRequest.size()),
            createPetRequest.description(),
            null,
            null
        );
    }

    public static PetResponse toResponse(PetDTO petDTO, String legalPersonId) {
        return new PetResponse(
            petDTO.id(),
            legalPersonId,
            petDTO.name(),
            petDTO.age(),
            petDTO.isVaccinated(),
            petDTO.type(),
            petDTO.size(),
            petDTO.description(),
            petDTO.createdAt(),
            petDTO.updatedAt()
        );
    }
}
