package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.YannyWordProducer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

// JUnit5 Tests

@ActiveProfiles("yanny")
// Anotación para usar las funcionalidades de Spring en los tests de esta clase y para crea un contexto de Spring
// a partir de los beans que se crean en las clases "HearingInterpreter" y "YannyWordProducer"
// Esta anotación ya incluye las anotaciones "@ExtendWith({SpringExtension.class})" y "@ContextConfiguration"
@SpringJUnitConfig(classes = { HearingInterpreter.class, YannyWordProducer.class })
class HearingInterpreterYannyTests {

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIHeardTest() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Yanny", word);
    }
}