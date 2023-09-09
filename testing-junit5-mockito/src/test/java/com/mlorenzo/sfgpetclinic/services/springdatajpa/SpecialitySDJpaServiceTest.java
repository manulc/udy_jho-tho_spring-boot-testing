package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import com.mlorenzo.sfgpetclinic.model.Speciality;
import com.mlorenzo.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

// Para poder usar anotaciones de Mockito
@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true)
    SpecialtyRepository specialtyRepository;

    @InjectMocks // Crea una instancia de esta clase e inyecta los mocks creados
    SpecialitySDJpaService specialitySDJpaService;

    @Test
    void deleteByIdTest() {
        specialitySDJpaService.deleteById(1L);
        // Verifica que exactamente se llamó al método "deleteById" del repositorio una vez con el argumento "1L"
        // Ejemplo de verificación usando un tiempo de timeout
        verify(specialtyRepository, timeout(100)).deleteById(1L);
        //verify(specialtyRepository, timeout(100).times(1)).deleteById(1L);
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void deleteByIdBDDTest() {
        // Given - none
        // When
        specialitySDJpaService.deleteById(1L);
        // Then
        // Verifica que exactamente se llamó al método "deleteById" del repositorio una vez con el argumento "1L"
        // Es equivalente a usar "verify" pero siguiendo la técnica BDD
        // Ejemplo de verificación usando un tiempo de timeout
        then(specialtyRepository).should(timeout(100)).deleteById(1L);
        //then(specialtyRepository).should(timeout(100).times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeastTest() {
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);
        // Verifica que al menos se llamó al método "deleteById" del repositorio una vez con el argumento "1L"
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void deleteByIdAtLeastBDDTest() {
        // Given - none
        // When
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);
        // Then
        // Verifica que al menos se llamó al método "deleteById" del repositorio una vez con el argumento "1L"
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMostTest() {
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);
        // Verifica que se llamó al método "deleteById" del repositorio menos de 5 veces con el argumento "1L"
        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void deleteByIdAtMostBDDTest() {
        // When
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);
        // Then
        // Verifica que se llamó al método "deleteById" del repositorio menos de 5 veces con el argumento "1L"
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNeverTest() {
        specialitySDJpaService.deleteById(1L);
        // Verifica que al menos se llamó al método "deleteById" del repositorio una vez con el argumento "1L"
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
        // Verifica que nunca se llamó al método "deleteById" del repositorio con el argumento "5L"
        verify(specialtyRepository, never()).deleteById(5L);
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void deleteByIdNeverBDDTest() {
        // When
        specialitySDJpaService.deleteById(1L);
        // Then
        // Verifica que al menos se llamó al método "deleteById" del repositorio una vez con el argumento "1L"
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
        // Verifica que nunca se llamó al método "deleteById" del repositorio con el argumento "5L"
        then(specialtyRepository).should(never()).deleteById(5L);
    }

    @Test
    void deleteByObjectTest() {
        Speciality speciality = new Speciality();
        specialitySDJpaService.delete(speciality);
        specialitySDJpaService.delete(speciality);
        // Verifica que exactamente se llamó al método "delete" del repositorio una vez con el argumento "speciality"
        // Argument Matchers -> "any(Speciality.class)"
        verify(specialtyRepository, times(2)).delete(any(Speciality.class));
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void deleteByObjectBDDTest() {
        // Given
        Speciality speciality = new Speciality();
        // When
        specialitySDJpaService.delete(speciality);
        specialitySDJpaService.delete(speciality);
        // Then
        // Verifica que exactamente se llamó al método "delete" del repositorio una vez con el argumento "speciality"
        // Argument Matchers -> "any(Speciality.class)"
        then(specialtyRepository).should(times(2)).delete(any(Speciality.class));
    }

    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();
        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));
        Speciality foundSpeciality = specialitySDJpaService.findById(1L);
        assertThat(foundSpeciality).isNotNull();
        // Argument Matchers -> "anyLong()"
        verify(specialtyRepository).findById(anyLong());
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void findByIdBDDTest() {
        // Given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(anyLong())).willReturn(Optional.of(speciality));
        // When
        Speciality foundSpeciality = specialitySDJpaService.findById(1L);
        // Then
        assertThat(foundSpeciality).isNotNull();
        // Verifica que exactamente se llamó al método "deleteById" del repositorio una vez y el repositorio no ha
        // tenido ninguna interacción más
        // Argument Matchers -> "anyLong()"
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void doThrowTest() {
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any(Speciality.class));
        assertThrows(RuntimeException.class, () -> specialitySDJpaService.delete(new Speciality()));
        verify(specialtyRepository).delete(any(Speciality.class));
    }

    // Técnica BDD con Mockito
    @Test
    void findByIdThrowsBDDTest() {
        given(specialtyRepository.findById(anyLong())).willThrow(new RuntimeException("boom"));
        assertThrows(RuntimeException.class, () -> specialitySDJpaService.findById(anyLong()));
        then(specialtyRepository).should().findById(anyLong());
    }

    // Técnica BDD con Mockito
    @Test
    void deleteThrowsBDDTest() {
        // En este caso, no podemos usar la expresión "given(...).willThrow(...);" porque el método "delete"
        // del repositorio no devuelve nada
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any(Speciality.class));
        assertThrows(RuntimeException.class, () -> specialitySDJpaService.delete(new Speciality()));
        then(specialtyRepository).should(times(1)).delete(any(Speciality.class));
    }

    // Técnica BDD con Mockito
    @Test
    void saveLambdaBDDTest() {
        // Given
        final String MATCH_ME = "MATCH_ME";
        final Speciality speciality = new Speciality();
        speciality.setId(1L);
        speciality.setDescription(MATCH_ME);
        // Lambda Argument Matchers
        // Retornará el objeto "speciality" cuando el método "save" del repositorio reciba un argumento de tipo
        // "Speciality" cuya descripción sea igual al valor de "MATCH_ME"
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME))))
                .willReturn(speciality);
        // When
        Speciality returnedSpeciality = specialitySDJpaService.save(speciality);
        // Then
        assertThat(returnedSpeciality.getId()).isEqualTo(1L);
        then(specialtyRepository).should(times(1)).save(any(Speciality.class));
    }

    // Técnica BDD con Mockito
    // Nota: Este test sólo pasa si se configura el Mock del repositorio con "lenient" igual a true para que la
    // coincidencia o el "match" no sea estricto, es decir, en este test se ejecuta el método "save" del servicio
    // con un argumento de entrada de tipo "Speciality" que no coincide o hace "match" con la condición indicada
    // en given
    @Test
    void saveLambdaNoMatchBDDTest() {
        // Given
        final Speciality speciality = new Speciality();
        speciality.setId(1L);
        speciality.setDescription("Not a match");
        // Lambda Argument Matchers
        // Retornará el objeto "speciality" cuando el método "save" del repositorio reciba un argumento de tipo
        // "Speciality" cuya descripción sea igual al valor de "MATCH_ME"
        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals("MATCH_ME"))))
                .willReturn(speciality);
        // When
        Speciality returnedSpeciality = specialitySDJpaService.save(speciality);
        // Then
        assertNull(returnedSpeciality);
        then(specialtyRepository).should(times(1)).save(any(Speciality.class));
    }
}