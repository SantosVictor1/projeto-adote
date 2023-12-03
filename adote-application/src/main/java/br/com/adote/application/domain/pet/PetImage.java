package br.com.adote.application.domain.pet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pet_image")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(PetImageListener.class)
public class PetImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String s3Url;
    @Column(nullable = false)
    private String fileName;
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
