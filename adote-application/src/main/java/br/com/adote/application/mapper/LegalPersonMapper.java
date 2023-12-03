package br.com.adote.application.mapper;

import br.com.adote.application.api.request.CreateLegalPersonRequest;
import br.com.adote.application.api.response.LegalPersonResponse;
import br.com.adote.application.domain.person.legal.LegalPerson;
import br.com.adote.application.dto.LegalPersonDTO;
import br.com.adote.application.enums.LegalPersonType;

public class LegalPersonMapper {

    public static LegalPerson toEntity(LegalPersonDTO legalPersonDTO) {
        return new LegalPerson(
                legalPersonDTO.id(),
                legalPersonDTO.name(),
                legalPersonDTO.age(),
                legalPersonDTO.email(),
                legalPersonDTO.createdAt(),
                legalPersonDTO.updatedAt(),
                legalPersonDTO.personType(),
                legalPersonDTO.cnpj(),
                legalPersonDTO.fantasyName(),
                legalPersonDTO.corporateName()
        );
    }

    public static LegalPersonDTO toDto(LegalPerson legalPerson) {
        return new LegalPersonDTO(
                legalPerson.getId(),
                legalPerson.getName(),
                legalPerson.getAge(),
                legalPerson.getEmail(),
                legalPerson.getPersonType(),
                legalPerson.getCnpj(),
                legalPerson.getFantasyName(),
                legalPerson.getCorporateName(),
                legalPerson.getCreatedAt(),
                legalPerson.getUpdatedAt()
        );
    }

    public static LegalPersonDTO toDto(CreateLegalPersonRequest createLegalPersonRequest) {
        return new LegalPersonDTO(
                null,
                createLegalPersonRequest.name(),
                createLegalPersonRequest.age(),
                createLegalPersonRequest.email(),
                LegalPersonType.valueOf(createLegalPersonRequest.personType()),
                createLegalPersonRequest.cnpj(),
                createLegalPersonRequest.fantasyName(),
                createLegalPersonRequest.corporateName(),
                null,
                null
        );
    }

    public static LegalPersonResponse toResponse(LegalPersonDTO legalPersonDTO) {
        return new LegalPersonResponse(
                legalPersonDTO.id(),
                legalPersonDTO.name(),
                legalPersonDTO.age(),
                legalPersonDTO.email(),
                legalPersonDTO.personType(),
                legalPersonDTO.cnpj(),
                legalPersonDTO.fantasyName(),
                legalPersonDTO.corporateName(),
                legalPersonDTO.createdAt(),
                legalPersonDTO.updatedAt()
        );
    }
}
