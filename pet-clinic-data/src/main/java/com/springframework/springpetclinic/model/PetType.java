package com.springframework.springpetclinic.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "pet_types")
public class PetType extends BaseEntity {
    private String name;
}
