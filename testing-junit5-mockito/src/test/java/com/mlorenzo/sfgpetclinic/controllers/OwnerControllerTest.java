package com.mlorenzo.sfgpetclinic.controllers;

import com.mlorenzo.sfgpetclinic.fauxspring.BindingResult;
import com.mlorenzo.sfgpetclinic.fauxspring.Model;
import com.mlorenzo.sfgpetclinic.model.Owner;
import com.mlorenzo.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS = "redirect:/owners/";
    private static final String VIEWS_OWNER_FIND_OWNERS = "owners/findOwners";
    private static final String VIEWS_OWNER_OWNERS_LIST = "owners/ownersList";

    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    OwnerController ownerController;

    // Técnica BDD con Mockito
    @Test
    void processCreationFormHasErrorsBDDTest() {
        // Given
        Owner owner = new Owner(null, "Joe", "Buck");
        given(bindingResult.hasErrors()).willReturn(true);
        // When
        String viewName = ownerController.processCreationForm(owner, bindingResult);
        // Then
        assertTrue(viewName.equals(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
        then(bindingResult).should(times(1)).hasErrors();
    }

    // Técnica BDD con Mockito
    @Test
    void processCreationFormNoErrorsBDDTest() {
        // Given
        Owner owner = new Owner(1L, "Joe", "Buck");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any(Owner.class))).willReturn(owner);
        // When
        String viewName = ownerController.processCreationForm(owner, bindingResult);
        // Then
        assertThat(viewName).isEqualTo(REDIRECT_OWNERS + owner.getId()); // Usando la librería "AssertJ"
        then(ownerService).should().save(any(Owner.class));
        then(bindingResult).should().hasErrors();
    }

    // Técnica BDD con Mockito
    // Creación de un ArgumentCaptor de Mockito en línea
    @Test
    void processFindFormWildcardStringBDDTest() {
        // Given
        Owner owner = new Owner(1L, "Joe", "Buck");
        List<Owner> ownerList = new ArrayList<>();
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        // Mockito Argument Capture - En este caso, capturamos el argumento que recibe el método "findAllByLastNameLike"
        // del servicio tras llamar al método "processFindForm" del controlador en "When"
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);
        // When
        String viewName = ownerController.processFindForm(owner, bindingResult, null);
        // Then
        assertThat(captor.getValue()).isEqualTo("%Buck%"); // Usando la librería "AssertJ"
        assertEquals(VIEWS_OWNER_FIND_OWNERS, viewName);
    }

    @Nested
    class ProcessFindFormMockitoAnswersTest {

        // Creación de un ArgumentCaptor de Mockito usando anotaciones
        @Captor
        ArgumentCaptor<String> stringArgumentCaptor;

        @Mock
        Model model;

        @BeforeEach
        void setUp() {
            // Given
            // Mockito Argument Capture - En este caso, capturamos el argumento que recibe el método
            // "findAllByLastNameLike" del servicio tras llamar al método "processFindForm" del controlador en "When"
            // Mockito Answers - En función del valor de un argumento de entrada, devolvemos un dato u otro
            given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                    .willAnswer(invocation -> {
                        String name = invocation.getArgument(0);
                        if(name.equals("%Buck%"))
                            return List.of(new Owner(1L, "Joe", "Buck"));
                        else if(name.equals("%DontFindMe%"))
                            return List.of();
                        else if(name.equals("%FindMe%"))
                            return List.of(
                                    new Owner(1L, "Joe", "Buck"),
                                    new Owner(2L, "Jim", "Bean")
                            );
                        throw new RuntimeException(("Invalid Argument"));
                    });
        }

        // Técnica BDD con Mockito
        @Test
        void processFindFormWildcardNotFoundBDDTest() {
            // Given
            Owner owner = new Owner(1L, "Joe", "DontFindMe");
            // When
            String viewName = ownerController.processFindForm(owner, bindingResult, model);
            // Then
            assertThat(stringArgumentCaptor.getValue()).isEqualTo("%DontFindMe%"); // Usando "AssertJ"
            assertThat(viewName).isEqualTo(VIEWS_OWNER_FIND_OWNERS);
            then(model).shouldHaveZeroInteractions();
            then(ownerService).should().findAllByLastNameLike(anyString());
            then(ownerService).shouldHaveNoMoreInteractions();
        }

        // Técnica BDD con Mockito
        @Test
        void processFindFormWildcardFoundOneBDDTest() {
            // Given
            Owner owner = new Owner(1L, "Joe", "Buck");
            // When
            String viewName = ownerController.processFindForm(owner, bindingResult, model);
            // Then
            assertThat(stringArgumentCaptor.getValue()).isEqualTo("%Buck%"); // Usando "AssertJ"
            assertThat(viewName).isEqualTo(REDIRECT_OWNERS + owner.getId());
            then(model).shouldHaveZeroInteractions();
            then(ownerService).should().findAllByLastNameLike(anyString());
            then(ownerService).shouldHaveNoMoreInteractions();
        }

        // Técnica BDD con Mockito
        @Test
        void processFindFormWildcardFoundMultipleBDDTest() {
            // Given
            // Para verificar el orden de uso de los Mocks cuando se invoca al método "processFindForm" del controlador
            InOrder inOrder = inOrder(ownerService, model);
            Owner owner = new Owner(1L, "Joe", "FindMe");
            // When
            String viewName = ownerController.processFindForm(owner, bindingResult, model);
            // Then
            assertThat(stringArgumentCaptor.getValue()).isEqualTo("%FindMe%"); // Usando "AssertJ"
            assertThat(viewName).isEqualTo(VIEWS_OWNER_OWNERS_LIST);
            // Verificamos que primero se utiliza el Mock "ownerService" y después el Mock "modelMock"
            //inOrder.verify(ownerService).findAllByLastNameLike(anyString()); // Sin usar BDD
            //inOrder.verify(model).addAttribute(anyString(), anyList()); // Sin usar BDD
            then(ownerService).should(inOrder).findAllByLastNameLike(anyString());
            then(model).should(inOrder).addAttribute(anyString(), anyList());
            then(model).shouldHaveNoMoreInteractions();
        }
    }
}