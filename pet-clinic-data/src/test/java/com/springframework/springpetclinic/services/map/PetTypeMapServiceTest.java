package com.springframework.springpetclinic.services.map;

import com.springframework.springpetclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetTypeMapServiceTest {

    private PetTypeMapService petTypeMapService;
    private Long petTypeId = 1L;

    @BeforeEach
    void setUp() {
        petTypeMapService = new PetTypeMapService();
        petTypeMapService.save(PetType.builder().id(petTypeId).build());
    }

    @Test
    void findAll() {
        Set<PetType> petTypes = petTypeMapService.findAll();
        assertEquals(1, petTypes.size());
    }

    @Test
    void findById() {
        PetType petType = petTypeMapService.findById(petTypeId);
        assertNotNull(petType);
        assertEquals(petTypeId, petType.getId());
    }

    @Test
    void findByWrongId() {
        Long wrongId = 7L;
        PetType petType = petTypeMapService.findById(wrongId);
        assertNull(petType);
    }

    @Test
    void findByNullId() {
        Long nullId = null;
        PetType petType = petTypeMapService.findById(nullId);
        assertNull(petType);
    }

    @Test
    void saveExistingId() {
        Long existingId = 2L;
        PetType savedPetType = petTypeMapService.save(PetType.builder().id(existingId).build());
        assertNotNull(savedPetType);
        assertEquals(existingId, petTypeMapService.findById(existingId).getId());
    }

    @Test
    void saveNoId() {
        PetType savedPetType = petTypeMapService.save(PetType.builder().build());
        assertNotNull(savedPetType);
        assertNotNull(savedPetType.getId());
    }

    @Test
    void deleteById() {
        petTypeMapService.deleteById(petTypeId);
        assertNull(petTypeMapService.findById(petTypeId));
        assertEquals(0, petTypeMapService.findAll().size());
    }

    @Test
    void deleteByWrongId() {
        Long wrongId = 17L;
        petTypeMapService.deleteById(wrongId);
        assertEquals(1, petTypeMapService.findAll().size());
    }

    @Test
    void deleteByNullId() {
        Long nullId = null;
        petTypeMapService.deleteById(nullId);
        assertEquals(1, petTypeMapService.findAll().size());
    }

    @Test
    void delete() {
        petTypeMapService.delete(petTypeMapService.findById(petTypeId));
        assertEquals(0, petTypeMapService.findAll().size());
    }

    @Test
    void deleteNullObject() {
        petTypeMapService.delete(null);
        assertEquals(1, petTypeMapService.findAll().size());
    }
}