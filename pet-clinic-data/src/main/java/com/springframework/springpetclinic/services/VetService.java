package com.springframework.springpetclinic.services;

import com.springframework.springpetclinic.model.Vet;

import java.util.Set;

public interface VetService {
    Vet getById(Long id);
    Vet getByLastName(String lastName);
    Vet save(Vet vet);
    Set<Vet> getAll();
}
