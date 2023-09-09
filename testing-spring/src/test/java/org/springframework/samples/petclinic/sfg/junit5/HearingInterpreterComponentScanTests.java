package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

// JUnit5 Tests

// Nota: Usamos las anotaciones "@ActiveProfiles("component-scan")" y "@Profile("component-scan")" para evitar que
// la clase de configuración interna "TestConfig" se tenga en cuenta cuando se ejecute otros tests que usan la anotación
// "@ComponentScan" sobre este paquete y así evitar conflictos entre los beans de Spring creados

@ActiveProfiles("component-scan")
// Anotación para usar las funcionalidades de Spring en los tests de esta clase y para crea un contexto de Spring
// a partir de los beans que se crean en las clases que hay dentro del paquete "org.springframework.samples.petclinic.sfg"
// Esta anotación ya incluye las anotaciones "@ExtendWith({SpringExtension.class})" y "@ContextConfiguration"
@SpringJUnitConfig(classes = { HearingInterpreterComponentScanTests.TestConfig.class })
class HearingInterpreterComponentScanTests {

    @Profile("component-scan")
    @Configuration
    // Nota: Esta anotación escanea este paquete tanto en el ámbito o "scope" "compile" como en el "test"
    @ComponentScan("org.springframework.samples.petclinic.sfg")
    static class TestConfig {

    }

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIHeardTest() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Laurel", word);
    }
}