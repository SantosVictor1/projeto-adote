package br.com.adote.application.repository;

import br.com.adote.application.domain.person.legal.LegalPerson;
import br.com.adote.application.domain.pet.Pet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, String> {
    Optional<Pet> findByIdAndLegalPerson(String id, LegalPerson legalPerson);
}
