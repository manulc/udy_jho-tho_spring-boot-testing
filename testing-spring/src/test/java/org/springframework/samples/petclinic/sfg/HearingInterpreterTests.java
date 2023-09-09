package org.springframework.samples.petclinic.sfg;

import org.junit.Test;

import static org.junit.Assert.*;

// JUnit4 Tests

public class HearingInterpreterTests {
    HearingInterpreter hearingInterpreter;

    @Test
    public void whatIHeardLaurelTest() {
        hearingInterpreter =  new HearingInterpreter(new LaurelWordProducer());
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Laurel", word);
    }

    @Test
    public void whatIHeardYannyTest() {
        hearingInterpreter =  new HearingInterpreter(new YannyWordProducer());
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Yanny", word);
    }
}