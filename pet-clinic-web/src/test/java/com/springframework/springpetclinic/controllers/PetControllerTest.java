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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private final String CREATE_OR_UPDATE_PET_FORM_VIEW = "pets/createOrUpdatePetForm";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
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
    void initCreationForm() throws Exception {


        //then
//        mockMvc.perform(get("owners/1/pets/new"))
//                .andExpect(status().isOk())
//                .andExpect(view().name(CREATE_OR_UPDATE_PET_FORM_VIEW))
//                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void processCreationForm() {
    }
}