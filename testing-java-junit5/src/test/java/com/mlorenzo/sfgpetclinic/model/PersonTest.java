package com.mlorenzo.sfgpetclinic.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest implements ModelTest {

    @Test
    void groupedAssertionsTest() {
        // BDD
        // given
        Person person = new Person(1L, "Joe", "Buck");
        // then
        assertAll("Test Props Set",
                () -> assertEquals("Joe", person.getFirstName()),
                () -> assertEquals("Buck", person.getLastName())
        );
    }

    @Test
    void groupedAssertionsMsgsTest() {
        // BDD
        // given
        Person person = new Person(1L, "Joe", "Buck");
        // then
        // Indicar un mensaje de error es opcional
        assertAll("Test Props Set",
                () -> assertEquals("Joe", person.getFirstName(), "First Name failed"),
                // Creación perezosa o "Lazy" del mensaje de error
                () -> assertEquals("Buck", person.getLastName(), () -> "Second Name failed")
        );
    }
}