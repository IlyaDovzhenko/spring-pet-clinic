package com.springframework.springpetclinic.services.map;

import com.springframework.springpetclinic.model.Specialty;
import com.springframework.springpetclinic.model.Vet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VetMapServiceTest {

    private VetMapService vetMapService;
    private final Long vetId = 1L;
    private final String vetLastName = "testVetLastName";

    @Mock
    SpecialtyMapService specialtyMapService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vetMapService = new VetMapService(specialtyMapService);
        Specialty specialty = Specialty.builder().id(10L).name("abc").build();
        Set<Specialty> specialties = new HashSet<>();
        specialties.add(specialty);
        when(specialtyMapService.findAll()).thenReturn(specialties);
        vetMapService.save(Vet.builder().id(vetId).lastName(vetLastName).specialties(specialtyMapService.findAll()).build());
    }

    @Test
    void findAll() {
        Set<Vet> vets = vetMapService.findAll();
        assertNotNull(vets);
        assertEquals(1, vets.size());
    }

    @Test
    void findById() {
        Vet vet = vetMapService.findById(vetId);
        assertNotNull(vet);
        assertEquals(vetId, vet.getId());
    }

    @Test
    void save() {
        vetMapService.save(Vet.builder().id(2L).specialties(specialtyMapService.findAll()).build());
        assertEquals(2, vetMapService.findAll().size());
    }

    @Test
    void saveNoId() {
        vetMapService.save(Vet.builder().specialties(specialtyMapService.findAll()).build());
        assertEquals(2, vetMapService.findAll().size());
    }

    @Test
    void deleteById() {
        vetMapService.deleteById(vetId);
        assertNull(vetMapService.findById(vetId));
        assertEquals(0, vetMapService.findAll().size());
    }

    @Test
    void deleteByWrongId() {
        Long wrongId = 7L;
        vetMapService.deleteById(wrongId);
        assertEquals(1, vetMapService.findAll().size());
    }

    @Test
    void deleteByNullId() {
        Long nullId = null;
        vetMapService.deleteById(nullId);
        assertEquals(1, vetMapService.findAll().size());
    }

    @Test
    void delete() {
        vetMapService.delete(vetMapService.findById(vetId));
        assertNull(vetMapService.findById(vetId));
        assertEquals(0, vetMapService.findAll().size());
    }

    @Test
    void deleteNull() {
        Vet nullVet = null;
        vetMapService.delete(nullVet);
        assertNotNull(vetMapService.findById(vetId));
        assertEquals(1, vetMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Vet vet = vetMapService.findByLastName(vetLastName);
        assertNotNull(vet);
        assertEquals(vetLastName, vet.getLastName());
    }

    @Test
    void findByWrongLastName() {
        String wrongLastName = "wrongName";
        Vet vet = vetMapService.findByLastName(wrongLastName);
        assertNull(vet);
    }
}