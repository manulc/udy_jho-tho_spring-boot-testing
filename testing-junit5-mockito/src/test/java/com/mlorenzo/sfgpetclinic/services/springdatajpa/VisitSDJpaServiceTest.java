package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import com.mlorenzo.sfgpetclinic.model.Visit;
import com.mlorenzo.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

// Para poder usar anotaciones de Mockito
@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService visitSDJpaService;

    @DisplayName("Test Find All")
    @Test
    void findAllTest() {
        Set<Visit> visitSet = Set.of(new Visit(), new Visit());
        when(visitRepository.findAll()).thenReturn(visitSet);
        Set<Visit> returnedVisitSet = visitSDJpaService.findAll();
        assertEquals(2, returnedVisitSet.size());
        assertThat(returnedVisitSet).hasSize(2); // Usando AssertJ
        verify(visitRepository).findAll();
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void findAllBDDTest() {
        // Given
        Set<Visit> visitSet = Set.of(new Visit(), new Visit());
        given(visitRepository.findAll()).willReturn(visitSet);
        // When
        Set<Visit> returnedVisitSet = visitSDJpaService.findAll();
        // Then
        assertEquals(2, returnedVisitSet.size());
        assertThat(returnedVisitSet).hasSize(2); // Usando AssertJ
        // Es equivalente a usar "verify" pero siguiendo la técnica BDD
        then(visitRepository).should().findAll();
    }

    @Test
    void findByIdTest() {
        Visit visit = new Visit();
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));
        Visit foundVisit = visitSDJpaService.findById(anyLong());
        assertNotNull(foundVisit);
        assertThat(foundVisit).isNotNull(); // Usando AssertJ
        verify(visitRepository, times(1)).findById(anyLong());
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void findByIdBDDTest() {
        // Given
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
        // When
        Visit foundVisit = visitSDJpaService.findById(anyLong());
        // Then
        assertNotNull(foundVisit);
        assertThat(foundVisit).isNotNull(); // Usando AssertJ
        then(visitRepository).should(times(1)).findById(anyLong());
    }

    @Test
    void saveTest() {
        Visit visit = new Visit();
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        Visit savedVisit = visitSDJpaService.save(new Visit());
        assertTrue(savedVisit != null);
        assertThat(savedVisit).isNotNull(); // Usando AssertJ
        verify(visitRepository, times(1)).save(any(Visit.class));
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void saveBDDTest() {
        // Given
        Visit visit = new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(visit);
        // When
        Visit savedVisit = visitSDJpaService.save(new Visit());
        // Then
        assertTrue(savedVisit != null);
        assertThat(savedVisit).isNotNull(); // Usando AssertJ
        then(visitRepository).should(times(1)).save(any(Visit.class));
    }

    @Test
    void deleteTest() {
        visitSDJpaService.delete(new Visit());
        verify(visitRepository, times(1)).delete(any(Visit.class));
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void deleteBDDTest() {
        // Given
        Visit visit = new Visit();
        // When
        visitSDJpaService.delete(visit);
        // Then
        then(visitRepository).should(times(1)).delete(any(Visit.class));
    }

    @Test
    void deleteByIdTest() {
        visitSDJpaService.deleteById(1L);
        verify(visitRepository).deleteById(anyLong());
    }

    // Mismo test que el anterior pero siguiendo la técnica BDD con Mockito
    @Test
    void deleteByIdBDDTest() {
        // When
        visitSDJpaService.deleteById(1L);
        // Then
        then(visitRepository).should().deleteById(anyLong());
    }
}