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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class OwnerController {

    private static final String CREATE_OR_UPDATE_OWNER_FORM_VIEW = "/owners/createOrUpdateForm";
    private static final String OWNER_DETAILS_VIEW = "/owners/ownerDetails";
    private static final String OWNERS_LIST_VIEW = "/owners/ownersList";
    private static final String FIND_OWNERS_VIEW = "/owners/findOwners";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/owners/find")
    public String findOwner(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return FIND_OWNERS_VIEW;
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
            return FIND_OWNERS_VIEW;
        } else if (findOwners.size() == 1) { //1 owner found
            owner = findOwners.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else { //multiple owners found
            model.addAttribute("owners", findOwners);
            return OWNERS_LIST_VIEW;
        }
    }

    @GetMapping("/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {
        ModelAndView modelAndView = new ModelAndView(OWNER_DETAILS_VIEW);
        modelAndView.addObject(ownerService.findById(ownerId));
        return modelAndView;
    }

    @GetMapping("/owners/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return CREATE_OR_UPDATE_OWNER_FORM_VIEW;
    }

    @PostMapping("/owners/new")
    public String processCreationForm(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CREATE_OR_UPDATE_OWNER_FORM_VIEW;
        } else {
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/owners/{ownerId}/edit")
    public String initUpdateForm(@PathVariable Long ownerId, Model model) {
        model.addAttribute("owner", ownerService.findById(ownerId));
        return CREATE_OR_UPDATE_OWNER_FORM_VIEW;
    }

    @PostMapping("/owners/{ownerId}/edit")
    public String processUpdateForm(@PathVariable Long ownerId,
                                    @Valid Owner owner,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CREATE_OR_UPDATE_OWNER_FORM_VIEW;
        } else {
            owner.setId(ownerId);
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }
}