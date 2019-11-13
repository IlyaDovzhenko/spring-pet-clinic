package com.springframework.springpetclinic.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Pet extends BaseEntity {
    @Getter
    @Setter
    private PetType petType;

    @Getter
    @Setter
    private Owner owner;

    @Getter
    @Setter
    private LocalDate birthDate;



}
