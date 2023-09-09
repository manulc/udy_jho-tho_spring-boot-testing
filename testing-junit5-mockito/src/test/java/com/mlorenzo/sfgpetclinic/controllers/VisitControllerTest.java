package com.mlorenzo.sfgpetclinic.controllers;

import com.mlorenzo.sfgpetclinic.model.Pet;
import com.mlorenzo.sfgpetclinic.model.Visit;
import com.mlorenzo.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    // Un "Spy" en un envoltorio o "wrapper" de una instancia real que se le puede dar seguimiento como
    // a un "Mock"(Por ejemplo, verificaciones)
    // En este caso, la instancia real es de tipo "PetMapService"(No se puede crear "Spies" sobre interfaces)
    @Spy
    PetMapService petService;

    @InjectMocks
    VisitController visitController;

    @Test
    void loadPetWithVisitBDDTest() {
        // Given
        Map<String, Object> model = new HashMap<>();
        Pet pet1 = new Pet(12L);
        Pet pet2 = new Pet(3L);
        // Como "petService" es un "Spy", podemos invocar realmente a sus métodos
        petService.save(pet1);
        petService.save(pet2);
        // Invoca al método real "findById" del servicio
        // Opcional porque, al ser un "Spy", por defecto se invoca al método real
        given(petService.findById(anyLong())).willCallRealMethod();
        // When
        Visit visit = visitController.loadPetWithVisit(12L, model);
        // Then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(12L);
        then(petService).should(times(1)).findById(anyLong());
    }

    @Test
    void loadPetWithVisitStubbingBDDTest() {
        // Given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(3L);
        // En este caso, no se invoca al método real "findById" del servicio porque aquí estamos indicando
        // que devuelve un determinado valor en vez de invocar al método real("Stubbing")
        given(petService.findById(anyLong())).willReturn(pet);
        // When
        Visit visit = visitController.loadPetWithVisit(anyLong(), model);
        // Then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(3L);
        then(petService).should(times(1)).findById(anyLong());
    }
}