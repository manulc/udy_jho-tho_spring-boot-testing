package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.PropertiesWordProducer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

// JUnit5 Tests

// Con esta anotación, indicamos el lugar donde se encuentra el archivo de propiedades para las pruebas de esta clase
// Se tiene en cuenta tanto el classpath del ámbito o "scope" "compile" como el de "test"
@TestPropertySource("classpath:laurel.properties")
// Anotación para usar las funcionalidades de Spring en los tests de esta clase y para crea un contexto de Spring
// a partir de los beans que se crean en las clases "HearingInterpreter" y "PropertiesWordProducer"
// Esta anotación ya incluye las anotaciones "@ExtendWith({SpringExtension.class})" y "@ContextConfiguration"
@SpringJUnitConfig(classes = { HearingInterpreter.class, PropertiesWordProducer.class })
class HearingInterpreterLaurelPropertiesTests {

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIHeardTest() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("laURel", word);
    }
}