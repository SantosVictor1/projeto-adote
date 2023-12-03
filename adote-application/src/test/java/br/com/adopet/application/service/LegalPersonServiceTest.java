package br.com.adote.application.service;

import br.com.adote.application.common.exceptions.PersonAlreadyExistsException;
import br.com.adote.application.common.exceptions.PersonNotFoundException;
import br.com.adote.application.domain.person.legal.LegalPerson;
import br.com.adote.application.dto.LegalPersonDTO;
import br.com.adote.application.enums.LegalPersonType;
import br.com.adote.application.enums.PersonType;
import br.com.adote.application.repository.LegalPersonRepository;
import br.com.adote.application.service.impl.LegalPersonServiceImpl;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LegalPersonServiceTest {
    private final LegalPersonRepository legalPersonRepository = mock(LegalPersonRepository.class);
    private final LegalPersonServiceImpl personService = new LegalPersonServiceImpl(legalPersonRepository);

    @Test
    public void shouldCreateLegalPerson() {
        String id = UUID.randomUUID().toString();
        OffsetDateTime createdAt = OffsetDateTime.now();
        LegalPersonDTO legalPersonDTO = createLegalPersonDTO(null, null);
        LegalPerson savedLegalPerson = createLegalPerson(id, createdAt);
        ArgumentCaptor<LegalPerson> captor = ArgumentCaptor.forClass(LegalPerson.class);

        when(legalPersonRepository.existsByCnpj(legalPersonDTO.cnpj())).thenReturn(false);
        when(legalPersonRepository.existsByEmail(legalPersonDTO.email())).thenReturn(false);
        when(legalPersonRepository.existsByCorporateName(legalPersonDTO.corporateName())).thenReturn(false);
        when(legalPersonRepository.save(any())).thenReturn(savedLegalPerson);

        LegalPersonDTO createdLegalPersonDTO = personService.create(legalPersonDTO);

        verify(legalPersonRepository, times(1)).save(captor.capture());

        assertNotNull(captor.getValue());
        assertNotNull(createdLegalPersonDTO);
        assertNotNull(createdLegalPersonDTO.id());
        assertNotNull(createdLegalPersonDTO.createdAt());
        assertSame(captor.getValue().getCnpj(), createdLegalPersonDTO.cnpj());
    }

    @Test
    public void shouldThrowExceptionWhenExistsByCnpj() {
        LegalPersonDTO legalPersonDTO = createLegalPersonDTO(null, null);

        when(legalPersonRepository.existsByCnpj(legalPersonDTO.cnpj())).thenReturn(true);

        PersonAlreadyExistsException personAlreadyExistsException = assertThrows(
            PersonAlreadyExistsException.class, () -> {
                personService.create(legalPersonDTO);
            }
        );

        assertNotNull(personAlreadyExistsException);
        assertSame(personAlreadyExistsException.getType(), PersonType.LEGAL);
        assertSame(personAlreadyExistsException.getMessage(), "CNPJ");
    }

    @Test
    public void shouldThrowExceptionWhenExistsByEmail() {
        LegalPersonDTO legalPersonDTO = createLegalPersonDTO(null, null);

        when(legalPersonRepository.existsByCnpj(legalPersonDTO.cnpj())).thenReturn(false);
        when(legalPersonRepository.existsByEmail(legalPersonDTO.email())).thenReturn(true);

        PersonAlreadyExistsException personAlreadyExistsException = assertThrows(
            PersonAlreadyExistsException.class, () -> {
                personService.create(legalPersonDTO);
            }
        );

        assertNotNull(personAlreadyExistsException);
        assertSame(personAlreadyExistsException.getType(), PersonType.LEGAL);
        assertSame(personAlreadyExistsException.getMessage(), "EMAIL");
    }

    @Test
    public void shouldThrowExceptionWhenExistsByCorporateName() {
        LegalPersonDTO legalPersonDTO = createLegalPersonDTO(null, null);

        when(legalPersonRepository.existsByCnpj(legalPersonDTO.cnpj())).thenReturn(false);
        when(legalPersonRepository.existsByEmail(legalPersonDTO.email())).thenReturn(false);
        when(legalPersonRepository.existsByCorporateName(legalPersonDTO.corporateName())).thenReturn(true);

        PersonAlreadyExistsException personAlreadyExistsException = assertThrows(
            PersonAlreadyExistsException.class, () -> {
                personService.create(legalPersonDTO);
            }
        );

        assertNotNull(personAlreadyExistsException);
        assertSame(personAlreadyExistsException.getType(), PersonType.LEGAL);
        assertSame(personAlreadyExistsException.getMessage(), "CORPORATE NAME");
    }

    @Test
    public void shouldFindLegalPersonById() {
        String id = UUID.randomUUID().toString();
        OffsetDateTime createdAt = OffsetDateTime.now();
        LegalPerson legalPerson = createLegalPerson(id, createdAt);

        when(legalPersonRepository.findById(id)).thenReturn(Optional.of(legalPerson));

        LegalPersonDTO legalPersonDTO = personService.findById(id);

        assertNotNull(legalPersonDTO);
        assertSame(legalPersonDTO.id(), id);
        assertSame(legalPersonDTO.createdAt(), createdAt);
        assertSame(legalPersonDTO.cnpj(), legalPerson.getCnpj());
    }

    @Test
    public void shouldNotFindLegalPersonById() {
        String id = UUID.randomUUID().toString();

        when(legalPersonRepository.findById(id)).thenReturn(Optional.empty());

        PersonNotFoundException personNotFoundException = assertThrows(
            PersonNotFoundException.class, () -> {
                personService.findById(id);
            }
        );

        assertNotNull(personNotFoundException);
        assertSame(personNotFoundException.getType(), PersonType.LEGAL);
    }

    private LegalPersonDTO createLegalPersonDTO(String id, OffsetDateTime createdAt) {
        return new LegalPersonDTO(
            id,
            "John Doe",
            18,
            "test@email.com",
            LegalPersonType.CAREGIVER,
            "08248706000189",
            "fantasy name",
            "John Doe LTDA",
            createdAt, null
        );
    }

    private LegalPerson createLegalPerson(String id, OffsetDateTime createdAt) {
        return new LegalPerson(
            id,
            "John Doe",
            18,
            "test@email.com",
            createdAt,
            null,
            LegalPersonType.CAREGIVER,
            "08248706000189",
            "fantasy name",
            "John Doe LTDA"
        );
    }
}
