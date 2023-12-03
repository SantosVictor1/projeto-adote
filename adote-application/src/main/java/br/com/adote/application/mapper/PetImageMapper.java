package br.com.adote.application.mapper;

import br.com.adote.application.api.response.PetImageResponse;
import br.com.adote.application.domain.pet.PetImage;
import br.com.adote.application.dto.PetImageDTO;

public class PetImageMapper {

    public static PetImageDTO toDto(PetImage petImage) {
        return new PetImageDTO(
            petImage.getId(),
            petImage.getS3Url(),
            petImage.getFileName(),
            petImage.getCreatedAt(),
            petImage.getUpdatedAt()
        );
    }

    public static PetImageResponse toResponse(PetImageDTO petImageDTO, String petId, String legalPersonId) {
        return new PetImageResponse(
            petImageDTO.id(),
            petId,
            legalPersonId,
            petImageDTO.s3Url(),
            petImageDTO.fileName(),
            petImageDTO.createdAt(),
            petImageDTO.updatedAt()
        );
    }
}
