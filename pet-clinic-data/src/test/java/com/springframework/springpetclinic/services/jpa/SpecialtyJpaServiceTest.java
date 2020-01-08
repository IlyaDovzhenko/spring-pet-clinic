package com.springframework.springpetclinic.services.jpa;

import com.springframework.springpetclinic.model.Specialty;
import com.springframework.springpetclinic.repositories.SpecialtyRepository;
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
class SpecialtyJpaServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialtyJpaService specialtyJpaService;

    private Specialty returnedSpecialty;
    private final Long specId = 1L;

    @BeforeEach
    void setUp() {
        returnedSpecialty = Specialty.builder().id(specId).build();
    }

    @Test
    void findAll() {
        Set<Specialty> returnedSpecialties = new HashSet<>();
        returnedSpecialties.add(returnedSpecialty);
        when(specialtyRepository.findAll()).thenReturn(returnedSpecialties);
        Set<Specialty> specialties = specialtyJpaService.findAll();
        assertNotNull(specialties);
        assertEquals(1, specialties.size());
        verify(specialtyRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(specialtyRepository.findById(anyLong())).thenReturn(Optional.of(returnedSpecialty));
        Specialty specialty = specialtyJpaService.findById(specId);
        assertNotNull(specialty);
        assertEquals(specId, specialty.getId());
        verify(specialtyRepository, times(1)).findById(anyLong());
    }

    @Test
    void findByIdNotFound() {
        when(specialtyRepository.findById(anyLong())).thenReturn(Optional.empty());
        Specialty specialty = specialtyJpaService.findById(specId);
        assertNull(specialty);
        verify(specialtyRepository, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        when(specialtyRepository.save(any())).thenReturn(returnedSpecialty);
        Specialty specialty = specialtyJpaService.save(returnedSpecialty);
        assertNotNull(specialty);
        assertEquals(specId, specialty.getId());
        verify(specialtyRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        specialtyJpaService.delete(returnedSpecialty);
        verify(specialtyRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        specialtyJpaService.deleteById(anyLong());
        verify(specialtyRepository, times(1)).deleteById(anyLong());
    }
}