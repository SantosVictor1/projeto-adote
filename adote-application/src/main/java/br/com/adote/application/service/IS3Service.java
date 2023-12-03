package br.com.adote.application.service;

import br.com.adote.application.dto.LegalPersonDTO;
import br.com.adote.application.dto.PetDTO;
import br.com.adote.application.dto.PetImageDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IS3Service {

    List<PetImageDTO> uploadFile(
        LegalPersonDTO legalPersonDTO,PetDTO petDTO, List<MultipartFile> multipartFiles
    ) throws IOException;
}

