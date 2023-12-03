package br.com.adote.application.service.impl;

import br.com.adote.application.common.exceptions.PersonAlreadyExistsException;
import br.com.adote.application.common.exceptions.PersonNotFoundException;
import br.com.adote.application.domain.person.legal.LegalPerson;
import br.com.adote.application.dto.LegalPersonDTO;
import br.com.adote.application.enums.PersonType;
import br.com.adote.application.mapper.LegalPersonMapper;
import br.com.adote.application.repository.LegalPersonRepository;
import br.com.adote.application.service.ILegalPersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LegalPersonServiceImpl implements ILegalPersonService {
    private final LegalPersonRepository legalPersonRepository;

    public LegalPersonServiceImpl(LegalPersonRepository legalPersonRepository) {
        this.legalPersonRepository = legalPersonRepository;
    }

    @Override
    public LegalPersonDTO create(LegalPersonDTO createLegalPersonDTO) {
        validateLegalPerson(createLegalPersonDTO);

        LegalPerson legalPerson = LegalPersonMapper.toEntity(createLegalPersonDTO);
        legalPerson = legalPersonRepository.save(legalPerson);

        return LegalPersonMapper.toDto(legalPerson);
    }

    @Override
    public LegalPersonDTO findById(String legalPersonId) {
        LegalPerson legalPerson = legalPersonRepository.findById(legalPersonId).orElseThrow(
            () -> new PersonNotFoundException(PersonType.LEGAL, "ID")
        );

        return LegalPersonMapper.toDto(legalPerson);
    }

    private void validateLegalPerson(LegalPersonDTO createLegalPersonDTO) {
        if (legalPersonRepository.existsByCnpj(createLegalPersonDTO.cnpj())) {
            throw new PersonAlreadyExistsException(PersonType.LEGAL, "CNPJ");
        }

        if (legalPersonRepository.existsByEmail(createLegalPersonDTO.email())) {
            throw new PersonAlreadyExistsException(PersonType.LEGAL, "EMAIL");
        }

        if (legalPersonRepository.existsByCorporateName(createLegalPersonDTO.corporateName())) {
            throw new PersonAlreadyExistsException(PersonType.LEGAL, "CORPORATE NAME");
        }
    }

}
