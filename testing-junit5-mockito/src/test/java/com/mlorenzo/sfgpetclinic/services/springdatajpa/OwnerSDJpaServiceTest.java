package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import com.mlorenzo.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Desactiva la ejecución de todos los test de esta clase de pruebas
@Disabled(value = "Disabled until we learn Mocking")
class OwnerSDJpaServiceTest {
    OwnerSDJpaService service;

    @BeforeEach
    void setUp() {
        service = new OwnerSDJpaService(null, null, null);
    }

    // Desactiva la ejecución de este test
    @Disabled
    @Test
    void findByLastName() {
        Owner foundOwner = service.findByLastName("Buck");
    }

    @Test
    void findAllByLastNameLike() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}