package com.springframework.springpetclinic.controllers;

import com.springframework.springpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping({"/owners","/owners/find","owners.html"})
    public String ownersList(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/owners_list";
    }

}
