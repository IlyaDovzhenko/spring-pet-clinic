package com.springframework.springpetclinic.formatters;

import com.springframework.springpetclinic.model.PetType;
import com.springframework.springpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetTypeFormatterTest {

    @Mock
    private PetTypeService petTypeService;

    @InjectMocks
    private PetTypeFormatter petTypeFormatter;

    private Set<PetType> petTypes = new HashSet<>();
    private PetType cat;
    private PetType dog;

    @BeforeEach
    void setUp() {
        cat = PetType.builder().id(1L).name("cat").build();
        dog = PetType.builder().id(2L).name("dog").build();
        petTypes.add(cat);
        petTypes.add(dog);
    }

    @Test
    void parse() throws ParseException {
        //given
        String text = "cat";

        //when
        when(petTypeService.findAll()).thenReturn(petTypes);
        PetType parsedPetType = petTypeFormatter.parse(text, new Locale("en", ""));

        //then
        verify(petTypeService, times(1)).findAll();
        assertNotNull(parsedPetType);
        assertEquals(text, parsedPetType.getName());
        assertEquals(cat.getId(), parsedPetType.getId());
    }

    @Test
    void parseNotFoundTest() throws ParseException {
        //given
        String text = "cat-dog";
        String exMessage = "Type not found: " + text;

        //when
        when(petTypeService.findAll()).thenReturn(petTypes);
        ParseException exception = assertThrows(ParseException.class,
                () -> petTypeFormatter.parse(text, new Locale("en", "")));

        //then
        assertNotNull(exception);
        assertEquals(exMessage, exception.getMessage());
    }

    @Test
    void print() {
        //when
        String printPetTypeName = petTypeFormatter.print(cat, new Locale("en", ""));

        //then
        assertNotNull(printPetTypeName);
        assertEquals(cat.getName(), printPetTypeName);
    }
}