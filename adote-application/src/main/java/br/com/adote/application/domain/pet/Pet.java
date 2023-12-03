package br.com.adote.application.domain.pet;

import br.com.adote.application.domain.person.legal.LegalPerson;
import br.com.adote.application.enums.PetSize;
import br.com.adote.application.enums.PetType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(PetListener.class)
public class Pet {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private Boolean isVaccinated;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PetType type;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PetSize size;
    private String description;
    @ManyToOne
    @JoinColumn(name = "legal_person_id", nullable = false)
    private LegalPerson legalPerson;
    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private List<PetImage> petImages;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
