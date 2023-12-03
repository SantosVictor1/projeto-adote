package br.com.adote.application.repository;

import br.com.adote.application.domain.person.legal.LegalPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalPersonRepository extends JpaRepository<LegalPerson, String> {
    Boolean existsByCnpj(String cnpj);

    Boolean existsByEmail(String email);

    Boolean existsByCorporateName(String corporateName);
}
