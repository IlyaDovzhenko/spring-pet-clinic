package com.springframework.springpetclinic.controllers;

import com.springframework.springpetclinic.model.Owner;
import com.springframework.springpetclinic.model.Pet;
import com.springframework.springpetclinic.model.PetType;
import com.springframework.springpetclinic.services.OwnerService;
import com.springframework.springpetclinic.services.PetService;
import com.springframework.springpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("owners/{ownerId}")
public class PetController {

    private static final String CREATE_OR_UPDATE_PET_FORM_VIEW = "pets/createOrUpdatePetForm";

    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    public PetController(PetService petService,
                         OwnerService ownerService,
                         PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetType() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Model model) {
        model.addAttribute("pet", Pet.builder().build());
        return CREATE_OR_UPDATE_PET_FORM_VIEW;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(@PathVariable Long ownerId,
                                      @Valid Pet pet,
                                      BindingResult bindingResult,
                                      Model model) {
        Owner owner = (Owner) model.getAttribute("owner");
        if (owner == null) {
            return CREATE_OR_UPDATE_PET_FORM_VIEW;
        }
        if (StringUtils.hasLength(pet.getName())
                && pet.isNew()
                && owner.getPet(pet.getName(), true) != null) {
            bindingResult.rejectValue("name", "duplicate", "already exists");
        }
        owner.getPets().add(pet);
        pet.setOwner(owner);
        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return CREATE_OR_UPDATE_PET_FORM_VIEW;
        } else {
            petService.save(pet);
            return "redirect:/owners/" + ownerId;
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable Long petId, Model model) {
        model.addAttribute("pet", petService.findById(petId));
        return CREATE_OR_UPDATE_PET_FORM_VIEW;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet, BindingResult bindingResult, Owner owner, Model model) {
        if (bindingResult.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return CREATE_OR_UPDATE_PET_FORM_VIEW;
        } else {
            Pet oldPet = petService.findById(pet.getId());
            if (pet.getName() != null) {
                oldPet.setName(pet.getName());
            }
            if (pet.getOwner() != null && ownerService.findById(pet.getOwner().getId()) != null) {
                oldPet.setOwner(pet.getOwner());
            }
            if (pet.getBirthDate() != null) {
                oldPet.setBirthDate(pet.getBirthDate());
            }
            if (pet.getPetType() != null) {
                oldPet.setPetType(pet.getPetType());
            }
            if (pet.getVisits() != null && pet.getVisits().size() > 0) {
                oldPet.setVisits(pet.getVisits());
            }
            petService.save(oldPet);
            ownerService.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
    }
}