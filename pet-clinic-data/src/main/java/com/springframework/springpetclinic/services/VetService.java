package com.springframework.springpetclinic.services;

import com.springframework.springpetclinic.model.Vet;

public interface VetService extends CrudService<Vet, Long> {
    Vet findByLastName(String lastName);
}
