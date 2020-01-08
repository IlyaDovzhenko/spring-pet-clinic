package com.springframework.springpetclinic.services.jpa;

import com.springframework.springpetclinic.model.Visit;
import com.springframework.springpetclinic.repositories.VisitRepository;
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
class VisitJpaServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitJpaService visitJpaService;

    private Visit returnedVisit;
    private final Long visitId = 1L;

    @BeforeEach
    void setUp() {
        returnedVisit = Visit.builder().id(visitId).build();
    }

    @Test
    void findAll() {
        Set<Visit> returnedVisits = new HashSet<>();
        returnedVisits.add(returnedVisit);
        when(visitRepository.findAll()).thenReturn(returnedVisits);
        Set<Visit> visits = visitJpaService.findAll();
        assertNotNull(visits);
        assertEquals(1, visits.size());
        verify(visitRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(visitRepository.findById(visitId)).thenReturn(Optional.of(returnedVisit));
        Visit visit = visitJpaService.findById(visitId);
        assertNotNull(visit);
        assertEquals(visitId, visit.getId());
        verify(visitRepository, times(1)).findById(visitId);
    }

    @Test
    void save() {
        when(visitRepository.save(returnedVisit)).thenReturn(returnedVisit);
        Visit visit = visitJpaService.save(returnedVisit);
        assertNotNull(visit);
        assertEquals(visitId, visit.getId());
        verify(visitRepository, times(1)).save(returnedVisit);
    }

    @Test
    void delete() {
        visitJpaService.delete(returnedVisit);
        verify(visitRepository, times(1)).delete(returnedVisit);
    }

    @Test
    void deleteById() {
        visitJpaService.deleteById(visitId);
        verify(visitRepository, times(1)).deleteById(visitId);
    }
}