package br.com.adote.application.service.impl;

import br.com.adote.application.common.exceptions.LegalPersonTypeException;
import br.com.adote.application.common.exceptions.PetNotFoundException;
import br.com.adote.application.domain.pet.Pet;
import br.com.adote.application.dto.LegalPersonDTO;
import br.com.adote.application.dto.PetDTO;
import br.com.adote.application.dto.PetImageDTO;
import br.com.adote.application.enums.LegalPersonType;
import br.com.adote.application.mapper.LegalPersonMapper;
import br.com.adote.application.mapper.PetMapper;
import br.com.adote.application.repository.PetRepository;
import br.com.adote.application.service.ILegalPersonService;
import br.com.adote.application.service.IPetService;
import br.com.adote.application.service.IS3Service;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class PetServiceImpl implements IPetService {
    private final PetRepository petRepository;
    private final ILegalPersonService personService;
    private final IS3Service s3Service;

    public PetServiceImpl(PetRepository petRepository, ILegalPersonService personService, IS3Service s3Service) {
        this.petRepository = petRepository;
        this.personService = personService;
        this.s3Service = s3Service;
    }

    @Override
    public List<PetDTO> batchCreate(List<PetDTO> createPetDTOList, String legalPersonId) {
        LegalPersonDTO legalPersonDTO = getLegalPerson(legalPersonId);
        List<Pet> pets = createPetDTOList.stream().map(
            petDTO -> PetMapper.toEntity(petDTO, legalPersonDTO)
        ).toList();

        pets = petRepository.saveAll(pets);

        return pets.stream().map(PetMapper::toDto).toList();
    }

    @Override
    public PetDTO findById(String id, String legalPersonId) {
        LegalPersonDTO legalPersonDTO = getLegalPerson(legalPersonId);
        Pet pet = petRepository.findByIdAndLegalPerson(
            id, LegalPersonMapper.toEntity(legalPersonDTO)
        ).orElseThrow(() -> new PetNotFoundException("ID"));

        return PetMapper.toDto(pet);
    }

    @Override
    public List<PetImageDTO> uploadImages(
        String id, String legalPersonId, List<MultipartFile> fileList
    ) throws IOException {
        LegalPersonDTO legalPersonDTO = getLegalPerson(legalPersonId);
        PetDTO petDTO = findById(id, legalPersonId);

        return s3Service.uploadFile(legalPersonDTO, petDTO, fileList);
    }

    private LegalPersonDTO getLegalPerson(String legalPersonId) {
        LegalPersonDTO legalPersonDTO = personService.findById(legalPersonId);

        if (legalPersonDTO.personType() != LegalPersonType.CAREGIVER) {
            throw new LegalPersonTypeException(legalPersonDTO.personType().name());
        }

        return legalPersonDTO;
    }
}
