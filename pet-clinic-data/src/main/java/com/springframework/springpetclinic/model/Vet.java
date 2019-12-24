package com.springframework.springpetclinic.model;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vets")
public class Vet extends Person {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vets_specialties",
            joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id")
    )
    private Set<Specialty> specialties = new HashSet<>();

    @OneToMany(mappedBy = "vet")
    private Set<Visit> visits = new HashSet<>();

    @Builder
    public Vet(Long id, String firstName, String lastName, Set<Specialty> specialties, Set<Visit> visits) {
        super(id, firstName, lastName);
        this.specialties = specialties;
        this.visits = visits;
    }
}
