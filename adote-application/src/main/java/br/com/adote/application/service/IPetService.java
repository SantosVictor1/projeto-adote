package br.com.adote.application.service;

import br.com.adote.application.dto.PetDTO;
import br.com.adote.application.dto.PetImageDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IPetService {

    List<PetDTO> batchCreate(List<PetDTO> createPetDTOList, String legalPersonId);

    PetDTO findById(String id, String legalPersonId);

    List<PetImageDTO> uploadImages(String id, String legalPersonId, List<MultipartFile> fileList) throws IOException;
}
