package org.springframework.samples.petclinic.sfg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

// JUnit4 Tests

// Anotación para usar las funcionalidades de Spring en los tests de esta clase
@RunWith(SpringRunner.class)
// Anotación que crea un contexto de Spring a partir de los beans que se crean en las clases "HearingInterpreter"
// y "LaurelWordProducer"
@ContextConfiguration(classes = { HearingInterpreter.class, LaurelWordProducer.class })
public class HearingInterpreterLaurelTests {

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    public void whatIHeardTest() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Laurel", word);
    }
}