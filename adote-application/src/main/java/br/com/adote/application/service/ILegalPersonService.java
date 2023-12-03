package br.com.adote.application.service;

import br.com.adote.application.dto.LegalPersonDTO;

public interface ILegalPersonService {

    LegalPersonDTO create(LegalPersonDTO createLegalPersonDTO);

    LegalPersonDTO findById(String legalPersonId);
}
