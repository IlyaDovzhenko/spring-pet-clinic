package com.springframework.springpetclinic.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Specialty extends BaseEntity {
    private String name;
    private String description;
}
