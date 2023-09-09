package com.mlorenzo.sfgpetclinic.controllers;

import com.mlorenzo.sfgpetclinic.fauxspring.BindingResult;
import com.mlorenzo.sfgpetclinic.fauxspring.WebDataBinder;
import com.mlorenzo.sfgpetclinic.model.Pet;
import com.mlorenzo.sfgpetclinic.model.Visit;
import com.mlorenzo.sfgpetclinic.services.PetService;
import com.mlorenzo.sfgpetclinic.services.VisitService;

import javax.validation.Valid;
import java.util.Map;

public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    public Visit loadPetWithVisit(Long petId, Map<String, Object> model) {
        Pet pet = petService.findById(petId);
        model.put("pet", pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    public String initNewVisitForm(Long petId, Map<String, Object> model) {
        return "pets/createOrUpdateVisitForm";
    }

    public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
        if (result.hasErrors())
            return "pets/createOrUpdateVisitForm";
        else {
            visitService.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }
}
