package com.springframework.springpetclinic.services.jpa;

import com.springframework.springpetclinic.model.Pet;
import com.springframework.springpetclinic.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class PetJpaServiceTest {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetJpaService petJpaService;

    Pet pet;

    @BeforeEach
    void setUp() {
        Pet pet = Pet.builder().build();
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}