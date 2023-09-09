package com.mlorenzo.sfgpetclinic.controllers;

import com.mlorenzo.sfgpetclinic.fauxspring.Model;
import com.mlorenzo.sfgpetclinic.fauxspring.ModelMapImpl;
import com.mlorenzo.sfgpetclinic.model.Vet;
import com.mlorenzo.sfgpetclinic.services.SpecialtyService;
import com.mlorenzo.sfgpetclinic.services.VetService;
import com.mlorenzo.sfgpetclinic.services.map.SpecialityMapService;
import com.mlorenzo.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

// Nota: Sin usar Mocks

class VetControllerTest implements ControllerTest {
    VetController controller;

    @BeforeEach
    void setUp() {
        SpecialtyService specialtyService = new SpecialityMapService();
        VetService vetService = new VetMapService(specialtyService);
        controller = new VetController(vetService);
        vetService.save(new Vet(1L, "Joe", "Buck", null));
        vetService.save(new Vet(2L, "Jhon", "Doe", null));
    }

    @Test
    void listVets() {
        Model model = new ModelMapImpl();
        String viewName = controller.listVets(model);
        HashSet<Vet> vets = (HashSet<Vet>)((ModelMapImpl)model).getMap().get("vets");
        assertTrue(viewName.equals("vets/index"));
        assertEquals(2, vets.size());
    }
}