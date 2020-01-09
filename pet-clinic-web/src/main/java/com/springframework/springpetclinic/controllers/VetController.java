package com.springframework.springpetclinic.controllers;

import com.springframework.springpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"/vets","/vets.html","/vets/find"})
    public String vetsList(Model model) {
        model.addAttribute("vets", vetService.findAll());
        return "vets/vets_list";
    }
}