package com.springframework.springpetclinic.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {
    @DateTimeFormat(pattern = "yyyy-MM-DD HH24:mm")
    private LocalDate date;
    private String description;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @Builder
    public Visit(Long id, LocalDate date, String description, Pet pet, Vet vet) {
        super(id);
        this.date = date;
        this.description = description;
        this.pet = pet;
        this.vet = vet;
    }
}