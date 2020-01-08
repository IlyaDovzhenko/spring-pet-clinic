package com.springframework.springpetclinic.services.jpa;

import com.springframework.springpetclinic.model.PetType;
import com.springframework.springpetclinic.repositories.PetTypeRepository;
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
class PetTypeJpaServiceTest {

    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private PetTypeJpaService petTypeJpaService;

    private PetType returnedPetType;
    private Long petTypeId = 1L;

    @BeforeEach
    void setUp() {
        returnedPetType = PetType.builder().id(petTypeId).build();
    }

    @Test
    void findAll() {
        Set<PetType> returnedPetTypes = new HashSet<>();
        returnedPetTypes.add(returnedPetType);
        when(petTypeRepository.findAll()).thenReturn(returnedPetTypes);
        Set<PetType> petTypes = petTypeJpaService.findAll();
        assertNotNull(petTypes);
        assertEquals(1, petTypes.size());
        verify(petTypeRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(petTypeRepository.findById(anyLong())).thenReturn(Optional.of(returnedPetType));
        PetType petType = petTypeJpaService.findById(petTypeId);
        assertNotNull(petType);
        assertEquals(petTypeId, petType.getId());
        verify(petTypeRepository, times(1)).findById(anyLong());
    }

    @Test
    void findByIdNotFound() {
        when(petTypeRepository.findById(anyLong())).thenReturn(Optional.empty());
        PetType petType = petTypeJpaService.findById(petTypeId);
        assertNull(petType);
        verify(petTypeRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        when(petTypeRepository.save(any())).thenReturn(returnedPetType);
        PetType petType = petTypeJpaService.save(returnedPetType);
        assertNotNull(petType);
        assertEquals(petTypeId, petType.getId());
        verify(petTypeRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        petTypeJpaService.delete(returnedPetType);
        verify(petTypeRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        petTypeJpaService.deleteById(petTypeId);
        verify(petTypeRepository, times(1)).deleteById(anyLong());
    }
}