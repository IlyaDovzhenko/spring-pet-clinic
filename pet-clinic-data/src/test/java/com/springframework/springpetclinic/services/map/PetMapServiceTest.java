package com.springframework.springpetclinic.services.map;

import com.springframework.springpetclinic.model.Pet;
import com.springframework.springpetclinic.model.Vet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetMapServiceTest {

    private PetMapService petMapService;
    private final Long petId = 1L;

    @BeforeEach
    void setUp() {
        petMapService = new PetMapService();
        petMapService.save(Pet.builder().id(petId).build());
    }

    @Test
    void findAll() {
        Set<Pet> pets = petMapService.findAll();
        assertEquals(1, pets.size());
    }

    @Test
    void findByIdExistingId() {
        Pet savedPet = petMapService.findById(petId);
        assertEquals(petId, savedPet.getId());
    }

    @Test
    void findByIdNotExistingId() {
        Long notExistingId = 5L;
        Long nullId = null;
        Pet savedPetNotExistingId = petMapService.findById(notExistingId);
        Pet savedPetNullId = petMapService.findById(nullId);
        assertNull(savedPetNotExistingId);
        assertNull(savedPetNullId);
    }

    @Test
    void saveExistingId() {
        Long id = 3L;
        Pet savedPet = petMapService.save(Pet.builder().id(id).build());
        assertNotNull(savedPet);
        assertEquals(savedPet, petMapService.findById(id));
    }

    @Test
    void saveNoId() {
        Pet savedPet = petMapService.save(Pet.builder().build());
        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());
    }

    @Test
    void deleteById() {
        petMapService.deleteById(petId);
        assertNull(petMapService.findById(petId));
        assertEquals(0, petMapService.findAll().size());
    }

    @Test
    void delete() {
        petMapService.delete(petMapService.findById(petId));
        assertNull(petMapService.findById(petId));
        assertEquals(0, petMapService.findAll().size());
    }
}