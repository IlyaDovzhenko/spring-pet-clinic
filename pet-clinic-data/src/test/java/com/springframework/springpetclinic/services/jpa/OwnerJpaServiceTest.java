package com.springframework.springpetclinic.services.jpa;

import com.springframework.springpetclinic.model.Owner;
import com.springframework.springpetclinic.repositories.OwnerRepository;
import com.springframework.springpetclinic.repositories.PetRepository;
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
class OwnerJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerJpaService ownerService;

    private Owner returnedOwner;

    private final Long ownerId = 1L;
    private final String ownerLastName = "OwnerName";

    @BeforeEach
    void setUp() {
        returnedOwner = Owner.builder().id(ownerId).lastName(ownerLastName).build();
    }

    @Test
    void findAll() {
        Set<Owner> returnedOwners = new HashSet<>();
        returnedOwners.add(returnedOwner);
        when(ownerRepository.findAll()).thenReturn(returnedOwners);
        Set<Owner> owners = ownerService.findAll();
        assertNotNull(owners);
        assertEquals(1, owners.size());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnedOwner));
        Owner owner = ownerService.findById(ownerId);
        assertNotNull(owner);
        assertEquals(ownerId, owner.getId());
        verify(ownerRepository, times(1)).findById(anyLong());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());
        Owner owner = ownerService.findById(2L);
        assertNull(owner);
        verify(ownerRepository, times(1)).findById(anyLong());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(anyString())).thenReturn(returnedOwner);
        Owner owner = ownerService.findByLastName(ownerLastName);
        assertEquals(ownerLastName, owner.getLastName());
        verify(ownerRepository, times(1)).findByLastName(any());
    }

    @Test
    void findByLastNameNotFound() {
        String wrongName = "wrongName";
        when(ownerRepository.findByLastName(anyString())).thenReturn(null);
        Owner owner = ownerService.findByLastName(wrongName);
        assertNull(owner);
        verify(ownerRepository, times(1)).findByLastName(anyString());
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(ownerId).build();
        when(ownerRepository.save(any())).thenReturn(ownerToSave);
        Owner owner = ownerService.save(ownerToSave);
        assertNotNull(owner);
        assertEquals(ownerToSave.getId(), owner.getId());
        verify(ownerRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        ownerService.delete(returnedOwner);
        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(ownerId);
        verify(ownerRepository, times(1)).deleteById(anyLong());
    }
}