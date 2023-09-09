package com.mlorenzo.sfgpetclinic.services.springdatajpa;

import com.mlorenzo.sfgpetclinic.junitextensions.TimingExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// Integration Tests
// Nota: Si el nombre de la clase finaliza en "IT", Maven detecta que esa clase es una clase de pruebas de integración

// Esta anotación "@ExtendWith" nos permite añadir funcionalidades de terceros a pruebas
// En este caso, se añade la funcionalidad proporcionada por la clase "TimingExtension" que calcula el tiempo que tarda
// en ejecutarse cada test
@ExtendWith(TimingExtension.class)
class PetTypeSDJpaServiceIT {

    @BeforeEach
    void setUp() {
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