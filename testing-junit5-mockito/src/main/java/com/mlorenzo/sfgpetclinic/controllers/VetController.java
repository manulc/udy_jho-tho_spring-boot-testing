package com.mlorenzo.sfgpetclinic.controllers;

import com.mlorenzo.sfgpetclinic.fauxspring.Model;
import com.mlorenzo.sfgpetclinic.services.VetService;

public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    public String listVets(Model model){
        model.addAttribute("vets", vetService.findAll());
        return "vets/index";
    }
}
