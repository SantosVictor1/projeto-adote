package br.com.adote.application.repository;

import br.com.adote.application.domain.pet.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetImageRepository extends JpaRepository<PetImage, Long> {
}
