package br.com.adote.application.domain.person;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(PersonListener.class)
public class Person {
    @Id
    private String id;
    private String name;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false, unique = true)
    private String email;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
