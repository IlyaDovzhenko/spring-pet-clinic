package com.springframework.springpetclinic.controllers;

import com.springframework.springpetclinic.services.OwnerService;
import com.springframework.springpetclinic.services.PetService;
import com.springframework.springpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    private PetService petService;

    @Mock
    private PetTypeService petTypeService;

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void populatePetType() {
    }

    @Test
    void findOwner() {
    }

    @Test
    void initOwnerBinder() {
    }

    @Test
    void initCreationForm() {
    }

    @Test
    void processCreationForm() {
    }
}