package br.com.adote.application.domain.person;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonListener {
    private static final Logger logger = LoggerFactory.getLogger(PersonListener.class);

    @PrePersist
    public void beforeSave(Person person) {
        person.setId(UUID.randomUUID().toString());
        person.setCreatedAt(OffsetDateTime.now());

        logger.info("Creating new person with ID: " + person.getId());
    }

    @PreUpdate
    public void beforeUpdate(Person person) {
        person.setUpdatedAt(OffsetDateTime.now());

        logger.info("Updating person with ID: " + person.getId());
    }
}
