package com.springframework.springpetclinic.controllers;

import com.springframework.springpetclinic.model.Owner;
import com.springframework.springpetclinic.model.Pet;
import com.springframework.springpetclinic.model.PetType;
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
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    private PetService petService;

    @Mock
    private PetTypeService petTypeService;

    @Mock
    private OwnerService ownerService;

    @Mock
    Model model;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;
    private final String CREATE_OR_UPDATE_PET_FORM_VIEW = "pets/createOrUpdatePetForm";

    private Owner owner;
    private Long ownerId = 1L;

    private Set<PetType> petTypes = new HashSet<>();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();

        owner = Owner.builder().id(ownerId).build();

        petTypes.add(PetType.builder().id(1L).name("Cat").build());
        petTypes.add(PetType.builder().id(2L).name("Dog").build());
    }

    @Test
    void initCreationForm() throws Exception {
        //then
        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_OR_UPDATE_PET_FORM_VIEW))
                .andExpect(model().attributeExists("pet"));
        verify(ownerService, times(1)).findById(anyLong());
        verify(petTypeService, times(1)).findAll();
    }

    @Test
    void processCreationForm() throws Exception {
        //when()
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petService.save(any())).thenReturn(Pet.builder().id(1L).build());

        //then
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(ownerService, times(1)).findById(anyLong());
        verify(petService).save(any());
    }

    @Test
    void processCreationFormWithNullOwner() throws Exception {
        //then
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(CREATE_OR_UPDATE_PET_FORM_VIEW));
        assertNull(model.getAttribute("owner"));
    }
}