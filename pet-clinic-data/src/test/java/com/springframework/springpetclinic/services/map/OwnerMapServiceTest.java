package com.springframework.springpetclinic.services.map;

import com.springframework.springpetclinic.model.Owner;
import com.springframework.springpetclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    private OwnerMapService ownerMapService;
    private final Long ownerId = 1L;
    private final String testLastName = "testLastName";

    @Mock
    PetTypeMapService petTypeMapService;

    @Mock
    PetMapService petMapService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ownerMapService = new OwnerMapService(petTypeMapService, petMapService);
        ownerMapService.save(Owner.builder().id(ownerId).lastName(testLastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner testOwner = Owner.builder().id(id).build();
        ownerMapService.save(testOwner);
        assertNotNull(ownerMapService.findById(id));
        assertEquals(id, ownerMapService.findById(id).getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner = ownerMapService.save(Owner.builder().build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void saveNullObject() {
        Owner savedOwner = ownerMapService.save(null);
        assertNull(savedOwner);
        assertEquals(1, ownerMapService.findAll().size());
    }

    @Test
    void saveException() {
        Set<Pet> pets = new HashSet<>();
        Pet pet = Pet.builder().id(2L).build();
        pets.add(pet);
        Owner owner = Owner.builder().pets(pets).build();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> ownerMapService.save(owner));
        assertEquals("Pet type is required!", exception.getMessage());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        assertNull(ownerMapService.findById(ownerId));
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));
        assertNull(ownerMapService.findById(ownerId));
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner testOwner = ownerMapService.findByLastName(testLastName);
        assertNotNull(testOwner);
        assertEquals(testLastName, ownerMapService.findByLastName(testLastName).getLastName());
    }

    @Test
    void findByLastNameNotFound() {
        String notExistName = "foo";
        Owner testOwner = ownerMapService.findByLastName(notExistName);
        assertNull(testOwner);
    }
}