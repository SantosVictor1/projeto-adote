package br.com.adote.application.service;

import br.com.adote.application.common.exceptions.LegalPersonTypeException;
import br.com.adote.application.common.exceptions.PetNotFoundException;
import br.com.adote.application.domain.person.legal.LegalPerson;
import br.com.adote.application.domain.pet.Pet;
import br.com.adote.application.dto.LegalPersonDTO;
import br.com.adote.application.dto.PetDTO;
import br.com.adote.application.dto.PetImageDTO;
import br.com.adote.application.enums.LegalPersonType;
import br.com.adote.application.enums.PetSize;
import br.com.adote.application.enums.PetType;
import br.com.adote.application.mapper.LegalPersonMapper;
import br.com.adote.application.repository.PetRepository;
import br.com.adote.application.service.impl.PetServiceImpl;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PetServiceTest {

    private final PetRepository petRepository = mock(PetRepository.class);
    private final ILegalPersonService legalPersonService = mock(ILegalPersonService.class);
    private final IS3Service s3Service = mock(IS3Service.class);
    private final IPetService petService = new PetServiceImpl(petRepository, legalPersonService, s3Service);

    @Test
    public void shouldCreatePetWithSuccess() {
        String legalPersonId = UUID.randomUUID().toString();
        OffsetDateTime createdAt = OffsetDateTime.now();
        LegalPersonType type = LegalPersonType.CAREGIVER;
        LegalPersonDTO legalPersonDTO = buildLegalPersonDTO(legalPersonId, type);
        List<Pet> savedPetList = buildPetList(
            List.of("02d57fb1-c5de-4d60-a535-632667feb031", "1ef6a518-8cc4-4082-9de3-bad47990eaaf"),
            createdAt,
            legalPersonDTO
        );
        List<PetDTO> petDTOList = buildPetDTOList(Arrays.asList(null, null));
        ArgumentCaptor<List<Pet>> captor = ArgumentCaptor.forClass(List.class);

        when(legalPersonService.findById(legalPersonId)).thenReturn(legalPersonDTO);
        when(petRepository.saveAll(any())).thenReturn(savedPetList);

        List<PetDTO> createdPetDTOList = petService.batchCreate(petDTOList, legalPersonId);

        verify(petRepository, times(1)).saveAll(captor.capture());

        assertNotNull(captor.getValue());
        assertNotNull(createdPetDTOList);
        assertSame(createdPetDTOList.size(), 2);
        createdPetDTOList.forEach(createdPetDTO -> assertNotNull(createdPetDTO.id()));
        createdPetDTOList.forEach(createdPetDTO -> assertNotNull(createdPetDTO.createdAt()));
        assertSame(createdPetDTOList.get(0).name(), petDTOList.get(0).name());
        assertSame(createdPetDTOList.get(1).name(), petDTOList.get(1).name());
    }

    @Test
    public void shouldThrowAnExceptionWhenLegalPersonTypeIsNotCaregiver() {
        String legalPersonId = UUID.randomUUID().toString();
        LegalPersonType type = LegalPersonType.VET;
        LegalPersonDTO legalPersonDTO = buildLegalPersonDTO(legalPersonId, type);
        List<PetDTO> petDTOList = buildPetDTOList(Arrays.asList(null, null));

        when(legalPersonService.findById(legalPersonId)).thenReturn(legalPersonDTO);

        LegalPersonTypeException legalPersonTypeException = assertThrows(
            LegalPersonTypeException.class, () -> {
                petService.batchCreate(petDTOList, legalPersonId);
            }
        );

        assertNotNull(legalPersonTypeException);
        assertNotNull(legalPersonTypeException.getMessage());
        assertSame(legalPersonTypeException.getMessage(), type.name());
    }

    @Test
    public void shouldFindByIdWithSuccess() {
        String legalPersonId = UUID.randomUUID().toString();
        String petId = UUID.randomUUID().toString();
        OffsetDateTime createdAt = OffsetDateTime.now();
        LegalPersonType type = LegalPersonType.CAREGIVER;
        LegalPersonDTO legalPersonDTO = buildLegalPersonDTO(legalPersonId, type);
        Pet pet = buildPet(petId, createdAt, legalPersonDTO);
        ArgumentCaptor<LegalPerson> captor = ArgumentCaptor.forClass(LegalPerson.class);

        when(legalPersonService.findById(legalPersonId)).thenReturn(legalPersonDTO);
        when(petRepository.findByIdAndLegalPerson(eq(petId), any())).thenReturn(Optional.of(pet));

        PetDTO foundedPetDTO = petService.findById(petId, legalPersonId);

        verify(petRepository, times(1)).findByIdAndLegalPerson(eq(petId), captor.capture());

        assertNotNull(foundedPetDTO);
        assertNotNull(foundedPetDTO.id());
        assertNotNull(foundedPetDTO.createdAt());
        assertSame(foundedPetDTO.id(), petId);
    }

    @Test
    public void shouldNotFindPet() {
        String legalPersonId = UUID.randomUUID().toString();
        String petId = UUID.randomUUID().toString();
        LegalPersonType type = LegalPersonType.CAREGIVER;
        LegalPersonDTO legalPersonDTO = buildLegalPersonDTO(legalPersonId, type);

        when(legalPersonService.findById(legalPersonId)).thenReturn(legalPersonDTO);
        when(petRepository.findByIdAndLegalPerson(eq(petId), any())).thenReturn(Optional.empty());

        PetNotFoundException petNotFoundException = assertThrows(
            PetNotFoundException.class, () -> {
                petService.findById(petId, legalPersonId);
        });

        assertNotNull(petNotFoundException);
        assertNotNull(petNotFoundException.getMessage());
    }

    @Test
    public void shouldUploadImageWithSuccess() throws IOException {
        String legalPersonId = UUID.randomUUID().toString();
        String petId = UUID.randomUUID().toString();
        OffsetDateTime createdAt = OffsetDateTime.now();
        LegalPersonType type = LegalPersonType.CAREGIVER;
        LegalPersonDTO legalPersonDTO = buildLegalPersonDTO(legalPersonId, type);
        Pet pet = buildPet(petId, createdAt, legalPersonDTO);
        PetDTO petDTO = buildPetDTOList(Arrays.asList(petId, null)).get(0);
        byte[] content = "File Content".getBytes();
        List<MultipartFile> multipartFiles = List.of(new MockMultipartFile(
            "file", "dennahy.png", "image/png", content));
        List<PetImageDTO> petImageDTOList = buildPetImageDTOList();
        ArgumentCaptor<PetDTO> petDTOCaptor = ArgumentCaptor.forClass(PetDTO.class);
        ArgumentCaptor<LegalPersonDTO> legalPersonDTOCaptor = ArgumentCaptor.forClass(LegalPersonDTO.class);

        when(legalPersonService.findById(legalPersonId)).thenReturn(legalPersonDTO);
        when(petRepository.findByIdAndLegalPerson(eq(petId), any())).thenReturn(Optional.of(pet));
        when(s3Service.uploadFile(any(), any(), eq(multipartFiles))).thenReturn(petImageDTOList);

        List<PetImageDTO> savedPetImageDTOList = petService.uploadImages(petId, legalPersonId, multipartFiles);

        verify(s3Service, times(1)).uploadFile(
            legalPersonDTOCaptor.capture(), petDTOCaptor.capture(), eq(multipartFiles));

        assertNotNull(savedPetImageDTOList);
        assertNotNull(legalPersonDTOCaptor.getValue());
        assertNotNull(petDTOCaptor.getValue());
        assertNotNull(savedPetImageDTOList);
        assertSame(savedPetImageDTOList.size(), 1);
        assertSame(multipartFiles.get(0).getOriginalFilename(), savedPetImageDTOList.get(0).fileName());
    }

    private List<PetDTO> buildPetDTOList(List<String> ids) {
        return List.of(
            new PetDTO(
                ids.get(0),
                "Dennahy",
                7,
                true,
                PetType.BIRD,
                PetSize.SMALL,
                "Um belo calopsito",
                null,
                null
            ),
            new PetDTO(
                ids.get(1),
                "Scotty",
                7,
                true,
                PetType.BIRD,
                PetSize.SMALL,
                "Outro belo calopsito",
                null,
                null
            )
        );
    }

    private List<Pet> buildPetList(List<String> ids, OffsetDateTime createdAt, LegalPersonDTO legalPersonDTO) {
        return List.of(new Pet(
            ids.get(0),
            "Dennahy",
            7,
            true,
            PetType.BIRD,
            PetSize.SMALL,
            "Um belo calopsito",
            LegalPersonMapper.toEntity(legalPersonDTO),
            null,
            createdAt,
            null
        ), new Pet(
            ids.get(1),
            "Scotty",
            7,
            true,
            PetType.BIRD,
            PetSize.SMALL,
            "Outro belo calopsito",
            LegalPersonMapper.toEntity(legalPersonDTO),
            null,
            createdAt,
            null
        ));
    }

    private Pet buildPet(String id, OffsetDateTime createdAt, LegalPersonDTO legalPersonDTO) {
        return new Pet(
            id,
            "Dennahy",
            7,
            true,
            PetType.BIRD,
            PetSize.SMALL,
            "Um belo calopsito",
            LegalPersonMapper.toEntity(legalPersonDTO),
            null,
            createdAt,
            null
        );
    }
    private LegalPersonDTO buildLegalPersonDTO(String id, LegalPersonType type) {
        return new LegalPersonDTO(
            id,
            "John Doe",
            18,
            "test@email.com",
            type,
            "08248706000189",
            "fantasy name",
            "John Doe LTDA",
            OffsetDateTime.parse("2023-06-01T19:31:47.296376083-03:00"),
            null
        );
    }

    private List<PetImageDTO> buildPetImageDTOList() {
        return List.of(
            new PetImageDTO(
                1L,
                "s3://s3-bucket/dennahy.png",
                "dennahy.png",
                OffsetDateTime.now(),
                null
            )
        );
    }
}
