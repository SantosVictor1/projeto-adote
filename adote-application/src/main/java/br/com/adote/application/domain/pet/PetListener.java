package br.com.adote.application.domain.pet;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetListener {
    private static final Logger logger = LoggerFactory.getLogger(PetListener.class);

    @PrePersist
    public void beforeSave(Pet pet) {
        pet.setId(UUID.randomUUID().toString());
        pet.setCreatedAt(OffsetDateTime.now());

        logger.info("Creating new pet with ID: " + pet.getId());
    }

    @PreUpdate
    public void beforeUpdate(Pet pet) {
        pet.setUpdatedAt(OffsetDateTime.now());

        logger.info("Updating pet with ID: " + pet.getId());
    }
}
