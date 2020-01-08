package com.springframework.springpetclinic.services.jpa;

import com.springframework.springpetclinic.model.Pet;
import com.springframework.springpetclinic.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetJpaServiceTest {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetJpaService petJpaService;

    private Pet returnedPet;
    private final Long petId = 1L;

    @BeforeEach
    void setUp() {
        returnedPet = Pet.builder().id(petId).build();
    }

    @Test
    void findAll() {
        Set<Pet> returnedPets = new HashSet<>();
        returnedPets.add(returnedPet);
        when(petRepository.findAll()).thenReturn(returnedPets);
        Set<Pet> pets = petJpaService.findAll();
        assertNotNull(pets);
        assertEquals(1, pets.size());
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(returnedPet));
        Pet pet = petJpaService.findById(petId);
        assertNotNull(pet);
        assertEquals(petId, pet.getId());
        verify(petRepository, times(1)).findById(anyLong());
    }

    @Test
    void findByIdNotFound() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());
        Pet pet = petJpaService.findById(petId);
        assertNull(pet);
        verify(petRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        Pet petToSave = Pet.builder().id(petId).build();
        when(petRepository.save(any())).thenReturn(petToSave);
        Pet pet = petJpaService.save(petToSave);
        assertNotNull(pet);
        assertEquals(petId, petToSave.getId());
        verify(petRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        petJpaService.delete(returnedPet);
        verify(petRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        petJpaService.deleteById(petId);
        verify(petRepository, times(1)).deleteById(anyLong());
    }
}