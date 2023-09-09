package com.mlorenzo.sfgpetclinic.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

// En vez de ejecutar todos los tests existentes, mediante los tags, podemos filtrar los tests que queremos ejecutar
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("controller")
interface ControllerTest {

    @BeforeAll
    default void beforeAll() {
        System.out.println("Lets do something here");
    }
}
