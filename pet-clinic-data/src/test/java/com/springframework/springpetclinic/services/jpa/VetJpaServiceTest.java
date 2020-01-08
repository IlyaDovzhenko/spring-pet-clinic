package com.springframework.springpetclinic.services.jpa;

import com.springframework.springpetclinic.model.Vet;
import com.springframework.springpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetJpaServiceTest {

    @Mock
    private VetRepository vetRepository;

    @InjectMocks
    private VetJpaService vetJpaService;

    private Vet returnedVet;
    private final Long vetId = 1L;
    private final String vetName = "vetName";

    @BeforeEach
    void setUp() {
        returnedVet = Vet.builder().id(vetId).lastName(vetName).build();
    }

    @Test
    void findByLastName() {
        when(vetRepository.findByLastName(vetName)).thenReturn(returnedVet);
        Vet vet = vetJpaService.findByLastName(vetName);
        assertNotNull(vet);
        assertEquals(vetName, vet.getLastName());
        verify(vetRepository, times(1)).findByLastName(vetName);
    }

    @Test
    void findByWrongLastName() {
        String wrongName = "wrongName";
        when(vetRepository.findByLastName(wrongName)).thenReturn(null);
        Vet vet = vetJpaService.findByLastName(wrongName);
        assertNull(vet);
        verify(vetRepository, times(1)).findByLastName(wrongName);
    }

    @Test
    void findAll() {
        Set<Vet> returnedVets = new HashSet<>();
        returnedVets.add(returnedVet);
        when(vetRepository.findAll()).thenReturn(returnedVets);
        Set<Vet> vets = vetJpaService.findAll();
        assertNotNull(vets);
        assertEquals(1, vets.size());
        verify(vetRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(vetRepository.findById(vetId)).thenReturn(Optional.of(returnedVet));
        Vet vet = vetJpaService.findById(vetId);
        assertNotNull(vet);
        assertEquals(vetId, vet.getId());
        verify(vetRepository, times(1)).findById(vetId);
    }

    @Test
    void save() {
        when(vetRepository.save(any())).thenReturn(returnedVet);
        Vet vet = vetJpaService.save(returnedVet);
        assertNotNull(vet);
        assertEquals(vetId, vet.getId());
        verify(vetRepository, times(1)).save(returnedVet);
    }

    @Test
    void delete() {
        vetJpaService.delete(returnedVet);
        verify(vetRepository, times(1)).delete(returnedVet);
    }

    @Test
    void deleteById() {
        vetJpaService.deleteById(vetId);
        verify(vetRepository, times(1)).deleteById(vetId);
    }
}