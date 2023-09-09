package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.LaurelWordProducer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

// JUnit5 Tests

// Nota: Usamos las anotaciones "@ActiveProfiles("inner-class")" y "@Profile("inner-class")" para evitar que la clase
// de configuración interna "TestConfig" se tenga en cuenta cuando se ejecute otros tests que usan la anotación
// "@ComponentScan" sobre este paquete y así evitar conflictos entre los beans de Spring creados

@ActiveProfiles("inner-class")
// Anotación para usar las funcionalidades de Spring en los tests de esta clase y para crea un contexto de Spring
// a partir del bean que se crea en las clase interna "TestConfig"
// Esta anotación ya incluye las anotaciones "@ExtendWith({SpringExtension.class})" y "@ContextConfiguration"
@SpringJUnitConfig(classes = { HearingInterpreterInnerClassTests.TestConfig.class })
class HearingInterpreterInnerClassTests {

    @Profile("inner-class")
    @Configuration
    static class TestConfig {

        @Bean
        HearingInterpreter hearingInterpreter() {
            return new HearingInterpreter(new LaurelWordProducer());
        }
    }

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIHeardTest() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Laurel", word);
    }
}