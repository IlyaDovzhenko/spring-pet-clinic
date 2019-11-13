package com.springframework.springpetclinic.services;

import com.springframework.springpetclinic.model.Pet;

import java.util.Set;

public interface PetService {
    Pet getById(Long id);
    Pet save(Pet pet);
    Set<Pet> getAll();
}
