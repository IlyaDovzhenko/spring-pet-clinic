package com.springframework.springpetclinic.services.map;

import com.springframework.springpetclinic.model.Owner;
import com.springframework.springpetclinic.model.Pet;
import com.springframework.springpetclinic.model.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VisitMapServiceTest {

    private VisitMapService visitMapService;
    private final Long visitId = 1L;
    private final Long petId = 1L;
    private final Long ownerId = 1L;

    @BeforeEach
    void setUp() {
        Owner owner = Owner.builder().id(ownerId).build();
        Pet pet = Pet.builder().id(petId).owner(owner).build();
        Visit visit = Visit.builder().id(visitId).pet(pet).build();
        visitMapService = new VisitMapService();
        visitMapService.save(visit);
    }

    @Test
    void findAll() {
        Set<Visit> visits = visitMapService.findAll();
        assertNotNull(visits);
        assertEquals(1, visits.size());
    }

    @Test
    void findById() {
        Visit visit = visitMapService.findById(visitId);
        assertNotNull(visit);
        assertEquals(visitId, visit.getId());
    }

    @Test
    void findByWrongId() {
        Long wrongId = 2L;
        Visit visit = visitMapService.findById(wrongId);
        assertNull(visit);
    }

    @Test
    void findByNullId() {
        Long nullId = null;
        Visit visit = visitMapService.findById(nullId);
        assertNull(visit);
    }

    @Test
    void save() {
        Long id = 2L;
        Owner owner = Owner.builder().id(id).build();
        Pet pet = Pet.builder().id(id).owner(owner).build();
        Visit visit = Visit.builder().id(id).pet(pet).build();
        visitMapService.save(visit);
        assertEquals(2, visitMapService.findAll().size());
    }

    @Test
    void saveWhenThrowException() {
        Visit visit = Visit.builder().build();
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> visitMapService.save(visit));
        assertEquals("Invalid visit!", exception.getMessage());
    }

    @Test
    void delete() {
        visitMapService.delete(visitMapService.findById(visitId));
        assertNull(visitMapService.findById(visitId));
        assertEquals(0, visitMapService.findAll().size());
    }

    @Test
    void deleteNull() {
        visitMapService.delete(null);
        assertEquals(1, visitMapService.findAll().size());
    }

    @Test
    void deleteById() {
        visitMapService.deleteById(visitId);
        assertNull(visitMapService.findById(visitId));
        assertEquals(0, visitMapService.findAll().size());
    }

    @Test
    void deleteByWrongId() {
        Long wrongId = 5L;
        visitMapService.deleteById(wrongId);
        assertEquals(1, visitMapService.findAll().size());
    }

    @Test
    void deleteByNullId() {
        visitMapService.deleteById(null);
        assertEquals(1, visitMapService.findAll().size());
    }
}