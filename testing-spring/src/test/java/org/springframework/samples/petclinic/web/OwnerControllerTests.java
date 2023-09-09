package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
// Anotación para usar las funcionalidades de Spring en los tests de esta clase y para crea un contexto de Spring
// a partir de los beans que se crean en los archivos XML "spring/mvc-test-config.xml" y "spring/mvc-core-config.xml"
// dentro del classpath(tanto en el ámbito o "scope" "compile" como en el "test")
// Esta anotación ya incluye las anotaciones "@ExtendWith({SpringExtension.class})" y "@ContextConfiguration"
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTests {

    @Autowired
    OwnerController ownerController;

    // Este bean de Spring es un Mock de la interfaz "ClinicService"
    @Autowired
    ClinicService clinicService;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    // Después de la ejecución de cada test, reseteamos o limpiamos el bean de Spring correspondiente al Mock de la
    // interfaz "ClinicService" para que las verificaciones de las interacciones de dicho Mock en cada test sean las
    // que corresponden
    @AfterEach
    void tearDown() {
        reset(clinicService);
    }

    @Test
    void initCreationFormTest() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void findOwnerNotFoundTest() throws Exception {
        mockMvc.perform(get("/owners")
                        .param("lastName", "Dont find me"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void findOwnerOneResultTest() throws Exception {
        Owner owner = new Owner();
        owner.setId(1);
        List<Owner> ownerList = List.of(owner);
        when(clinicService.findOwnerByLastName(anyString())).thenReturn(ownerList);
        mockMvc.perform(get("/owners")
                        .param("lastName", anyString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + owner.getId()));
        verify(clinicService, times(1)).findOwnerByLastName(anyString());
        verifyNoMoreInteractions(clinicService);
    }

    @Test
    void findOwnerMultipleResultsBDDTest() throws Exception {
        // Given
        List<Owner> ownerList = List.of(new Owner(), new Owner());
        given(clinicService.findOwnerByLastName(stringArgumentCaptor.capture())).willReturn(ownerList);
        // When
        mockMvc.perform(get("/owners"))
                // Then
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("selections"))
                .andExpect(view().name("owners/ownersList"));
        then(clinicService).should().findOwnerByLastName(anyString());
        then(clinicService).shouldHaveNoMoreInteractions();
        assertThat(stringArgumentCaptor.getValue()).isEqualTo("");
    }

    @Test
    void newOwnerPostValidTest() throws Exception {
        mockMvc.perform(post("/owners/new")
                        .param("firstName", "Jhon")
                        .param("lastName", "Doe")
                        .param("address", "123 Duval St")
                        .param("city", "Key West")
                        .param("telephone", "3151231234"))
                .andExpect(status().is3xxRedirection());
        verify(clinicService).saveOwner(any(Owner.class));
    }

    @Test
    void newOwnerPostNotValidTest() throws Exception {
        mockMvc.perform(post("/owners/new")
                        .param("firstName", "Jhon")
                        .param("lastName", "Doe")
                        .param("city", "Key West"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
        verifyZeroInteractions(clinicService);
    }

    @Test
    void updateOwnerValidPostTest() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
                        .param("firstName", "Jhon")
                        .param("lastName", "Doe")
                        .param("address", "123 Duval St")
                        .param("city", "Key West")
                        .param("telephone", "3151231234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
        verify(clinicService).saveOwner(any(Owner.class));
    }

    @Test
    void updateOwnerNotValidPostTest() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
                        .param("firstName", "Jhon")
                        .param("lastName", "Doe")
                        .param("address", "123 Duval St"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "city"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
        verifyZeroInteractions(clinicService);
    }

}