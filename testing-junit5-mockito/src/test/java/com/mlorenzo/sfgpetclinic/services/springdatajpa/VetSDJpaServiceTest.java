package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import com.mlorenzo.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

// Para poder usar anotaciones de Mockito
@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {

    @Mock
    VetRepository vetRepository;

    VetSDJpaService vetSDJpaService;

    @BeforeEach
    void setUp() {
        // Inyectamos el Mock de tipo "VetRepository"
        vetSDJpaService = new VetSDJpaService(vetRepository);
    }

    @Test
    void testDeleteById() {
        vetSDJpaService.deleteById(1L);
        // Verifica que exactamente se llamó al método "deleteById" del repositorio una vez con el argumento "1L"
        verify(vetRepository).deleteById(1L);
    }
}