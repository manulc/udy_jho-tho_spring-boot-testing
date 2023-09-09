package com.mlorenzo.sfgpetclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;

// En vez de ejecutar todos los tests existentes, mediante los tags, podemos filtrar los tests que queremos ejecutar
@Tag("repeated")
interface ModelRepeatedTest {

    @BeforeEach
    default void setUp(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.println("Running Test - " + testInfo.getDisplayName() + " - "
                + repetitionInfo.getCurrentRepetition());
    }
}
