package com.springframework.springpetclinic.controllers.vets;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/vets")
@Controller
public class VetController {

    @GetMapping({"","/vets"})
    public String listVets(Model model) {
        return "vets/vets_list";
    }

}
