package com.springframework.springpetclinic.controllers;

import com.springframework.springpetclinic.services.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping({"/pets","pets.html"})
    public String petsList(Model model) {
        model.addAttribute("pets", petService.findAll());
        return "pets/pets_list";
    }

    @GetMapping("/pets/find")
    public String findPet() {
        return "notImplemented";
    }
}