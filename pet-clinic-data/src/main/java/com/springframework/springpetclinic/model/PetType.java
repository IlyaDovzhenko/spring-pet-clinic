package com.springframework.springpetclinic.model;

import lombok.Getter;
import lombok.Setter;

public class PetType extends BaseEntity {
    @Getter
    @Setter
    private String name;
}
