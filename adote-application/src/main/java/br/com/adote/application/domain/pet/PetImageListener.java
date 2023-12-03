package br.com.adote.application.domain.pet;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetImageListener {
    private static final Logger logger = LoggerFactory.getLogger(PetImageListener.class);

    @PrePersist
    public void beforeSave(PetImage petImage) {
        petImage.setCreatedAt(OffsetDateTime.now());
    }

    @PostPersist
    public void afterSave(PetImage petImage) {
        logger.info("Creating new petImage with ID: " + petImage.getId());
    }

    @PreUpdate
    public void beforeUpdate(PetImage petImage) {
        petImage.setUpdatedAt(OffsetDateTime.now());

        logger.info("Updating petImage with ID: " + petImage.getId());
    }
}
