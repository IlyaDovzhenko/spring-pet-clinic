package com.springframework.springpetclinic.model;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "specialties")
public class Specialty extends BaseEntity {
    private String name;
    private String description;

    @Builder
    public Specialty(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }
}
