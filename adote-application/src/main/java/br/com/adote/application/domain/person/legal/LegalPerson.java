package br.com.adote.application.domain.person.legal;

import br.com.adote.application.domain.person.Person;
import br.com.adote.application.domain.pet.Pet;
import br.com.adote.application.enums.LegalPersonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "legal_person")
@NoArgsConstructor
@Getter
@Setter
public class LegalPerson extends Person {
    @Enumerated(value = EnumType.STRING)
    private LegalPersonType personType;
    @Column(nullable = false, unique = true)
    private String cnpj;
    @Column(nullable = false)
    private String fantasyName;
    @Column(nullable = false, unique = true)
    private String corporateName;
    @OneToMany(mappedBy = "legalPerson", fetch = FetchType.LAZY)
    List<Pet> pets;

    public LegalPerson(String id, String name, int age, String email, OffsetDateTime createdAt,
                       OffsetDateTime updatedAt, LegalPersonType personType, String cnpj, String fantasyName,
                       String corporateName
    ) {
        super(id, name, age, email, createdAt, updatedAt);
        this.personType = personType;
        this.cnpj = cnpj;
        this.fantasyName = fantasyName;
        this.corporateName = corporateName;
    }
}
