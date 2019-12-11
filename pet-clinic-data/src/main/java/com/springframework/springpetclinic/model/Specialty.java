package com.springframework.springpetclinic.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "specialties")
public class Specialty extends BaseEntity {
    private String name;
    private String description;
}
