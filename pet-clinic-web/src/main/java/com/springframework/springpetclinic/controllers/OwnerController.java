package com.springframework.springpetclinic.controllers;

import com.springframework.springpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping({"/owners","/owners.html"})
    public String ownersList(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/owners_list";
    }

    @GetMapping("/owners/find")
    public String findOwner() {
        return "notImplemented";
    }

    @GetMapping("/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {
        ModelAndView modelAndView = new ModelAndView("/owners/ownerDetails");
        modelAndView.addObject(ownerService.findById(ownerId));
        return modelAndView;
    }
}