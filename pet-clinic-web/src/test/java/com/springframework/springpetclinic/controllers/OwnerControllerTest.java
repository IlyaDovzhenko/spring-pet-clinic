package com.springframework.springpetclinic.controllers;

import com.springframework.springpetclinic.model.Owner;
import com.springframework.springpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    private MockMvc mockMvc;

    private Set<Owner> owners;
    private final Long firstOwnerId = 1L;
    private final Long secondOwnerId = 2L;
    private final String firstOwnerName = "firstOwner";
    private final String secondOwnerName = "secondOwner";
    private List<Owner> returnedList;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().id(firstOwnerId).lastName(firstOwnerName).build());
        owners.add(Owner.builder().id(secondOwnerId).lastName(secondOwnerName).build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .build();

        returnedList = new ArrayList<>();
    }

    @Test
    void findOwner() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("/owners/findOwners"))
                .andExpect(model().attributeExists("owner"));
        verifyNoInteractions(ownerService);
    }

    @Test
    void showOwnerTest() throws Exception {
        //given
        Owner returnedOwner = Owner.builder().id(firstOwnerId).firstName(firstOwnerName).build();

        //when
        when(ownerService.findById(anyLong())).thenReturn(returnedOwner);

        //then
        mockMvc.perform(get("/owners/" + firstOwnerId))
                .andExpect(status().isOk())
                .andExpect(view().name("/owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(firstOwnerId))));
    }

    @Test
    void processFindFormFindNoOneTest() throws Exception {
        //when
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(returnedList);

        //then
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("/owners/findOwners"));
    }

    @Test
    void processFindFormFindOneTest() throws Exception {
        //given
        returnedList.add(Owner.builder().id(firstOwnerId).build());

        //when
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(returnedList);

        //then
        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + firstOwnerId));
    }

    @Test
    void processFindFormFindManyTest() throws Exception {
        //given
        returnedList.add(Owner.builder().id(firstOwnerId).build());
        returnedList.add(Owner.builder().id(secondOwnerId).build());

        //when
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(returnedList);

        //then
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("/owners/ownersList"))
                .andExpect(model().attributeExists("owners"));
    }
}