package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.LaurelWordProducer;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

// JUnit5 Tests

// Anotación para usar las funcionalidades de Spring en los tests de esta clase y para crea un contexto de Spring
// a partir de los beans que se crean en las clases "HearingInterpreter" y "LaurelWordProducer"
// Esta anotación ya incluye las anotaciones "@ExtendWith({SpringExtension.class})" y "@ContextConfiguration"
@SpringJUnitConfig(classes = { HearingInterpreter.class, LaurelWordProducer.class })
class HearingInterpreterLaurelTests {

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIHeardTest() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Laurel", word);
    }
}