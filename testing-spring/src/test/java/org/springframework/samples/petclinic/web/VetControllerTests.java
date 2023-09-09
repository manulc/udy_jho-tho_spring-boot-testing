package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTests {

    @Mock
    ClinicService clinicService;

    @InjectMocks
    VetController vetController;

    List<Vet> vetList;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        vetList = List.of(new Vet(), new Vet());
        // Configuramos el Mock de Spring MVC sobre el controlador "vetController"
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }

    @Test
    void showVetListTest() {
        // Creación de un Mock en línea
        Map<String, Object> model = mock(Map.class);
        when(clinicService.findVets()).thenReturn(vetList);
        String viewName = vetController.showVetList(model);
        assertEquals("vets/vetList", viewName);
        verify(clinicService, times(1)).findVets();
        verify(model, times(1)).put(anyString(), any(Vets.class));
        verifyNoMoreInteractions(clinicService);
        verifyNoMoreInteractions(model);
    }

    // Este test utiliza el framework Spring MVC con objetos Mocks
    @Test
    void controllerShowVetListTest() throws Exception {
        mockMvc.perform(get("/vets.html"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/vetList"));
    }

    @Test
    void showResourcesVetListBDDTest() {
        // Given
        given(clinicService.findVets()).willReturn(vetList);
        // When
        Vets returnedVets = vetController.showResourcesVetList();
        // Then
        assertNotNull(returnedVets);
        assertEquals(2, returnedVets.getVetList().size());
        then(clinicService).should(times(1)).findVets();
        then(clinicService).shouldHaveNoMoreInteractions();
    }
}