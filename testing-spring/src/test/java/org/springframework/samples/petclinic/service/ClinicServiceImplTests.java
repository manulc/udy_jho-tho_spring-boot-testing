package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTests {

    @Mock
    PetRepository petRepository;

    @InjectMocks
    ClinicServiceImpl clinicService;

    @Test
    void findPetTypesTest() {
        List<PetType> petTypeList = List.of(new PetType(), new PetType());
        when(petRepository.findPetTypes()).thenReturn(petTypeList);
        Collection<PetType> returnedPetTypeList = clinicService.findPetTypes();
        assertNotNull(returnedPetTypeList);
        assertEquals(2, returnedPetTypeList.size());
        verify(petRepository).findPetTypes();
    }
}