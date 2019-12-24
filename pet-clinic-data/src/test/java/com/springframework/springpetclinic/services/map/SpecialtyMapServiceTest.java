package com.springframework.springpetclinic.services.map;

import com.springframework.springpetclinic.model.Specialty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyMapServiceTest {

    private SpecialtyMapService specialtyMapService;
    private final Long specialtyId = 1L;

    @BeforeEach
    void setUp() {
        specialtyMapService = new SpecialtyMapService();
        specialtyMapService.save(Specialty.builder().id(specialtyId).build());
    }

    @Test
    void findAll() {
        Set<Specialty> specialties = specialtyMapService.findAll();
        assertNotNull(specialties);
        assertEquals(1, specialties.size());
    }

    @Test
    void findById() {
        Specialty specialty = specialtyMapService.findById(specialtyId);
        assertNotNull(specialty);
        assertEquals(specialtyId, specialty.getId());
    }

    @Test
    void findByWrongId() {
        Long wrongId = 2L;
        Specialty specialty = specialtyMapService.findById(wrongId);
        assertNull(specialty);
    }

    @Test
    void findByNullId() {
        Long nullId = null;
        Specialty specialty = specialtyMapService.findById(nullId);
        assertNull(specialty);
    }

    @Test
    void saveExistingId() {
        Long existingId = 2L;
        Specialty specialty = specialtyMapService.save(Specialty.builder().id(existingId).build());
        assertNotNull(specialty);
        assertEquals(specialty, specialtyMapService.findById(existingId));
        assertEquals(existingId, specialtyMapService.findById(existingId).getId());
        assertEquals(2, specialtyMapService.findAll().size());
    }

    @Test
    void saveNoId() {
        Specialty specialty = specialtyMapService.save(Specialty.builder().build());
        assertNotNull(specialty);
        assertNotNull(specialty.getId());
    }

    @Test
    void delete() {
        specialtyMapService.delete(specialtyMapService.findById(specialtyId));
        assertEquals(0, specialtyMapService.findAll().size());
    }

    @Test
    void deleteNullObject() {
        specialtyMapService.delete(null);
        assertEquals(1, specialtyMapService.findAll().size());
    }

    @Test
    void deleteById() {
        specialtyMapService.deleteById(specialtyId);
        assertEquals(0, specialtyMapService.findAll().size());
    }

    @Test
    void deleteByWrongId() {
        Long wrongId = 3L;
        specialtyMapService.deleteById(wrongId);
        assertEquals(1, specialtyMapService.findAll().size());
    }

    @Test
    void deleteByNullId() {
        Long nullId = null;
        specialtyMapService.deleteById(nullId);
        assertEquals(1, specialtyMapService.findAll().size());
    }
}