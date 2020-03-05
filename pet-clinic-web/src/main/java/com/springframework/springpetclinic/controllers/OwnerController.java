package com.springframework.springpetclinic.controllers;

import com.springframework.springpetclinic.model.Owner;
import com.springframework.springpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

//    @GetMapping({"/owners","/owners.html"})
//    public String ownersList(Model model) {
//        model.addAttribute("owners", ownerService.findAll());
//        return "owners/ownersList";
//    }

    @GetMapping("/owners/find")
    public String findOwner(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping("/owners")
    public String processFindForm(Owner owner, BindingResult bindingResult, Model model) {
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }
        //find owners by last name
        List<Owner> findOwners = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
        if (findOwners.isEmpty()) { //no owners found
            bindingResult.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (findOwners.size() == 1) { //1 owner found
            owner = findOwners.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else { //multiple owners found
            model.addAttribute("owners", findOwners);
            return "owners/ownersList";
        }
    }

    @GetMapping("/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {
        ModelAndView modelAndView = new ModelAndView("/owners/ownerDetails");
        modelAndView.addObject(ownerService.findById(ownerId));
        return modelAndView;
    }
}