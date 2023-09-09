package com.mlorenzo.sfgpetclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;

// En vez de ejecutar todos los tests existentes, mediante los tags, podemos filtrar los tests que queremos ejecutar
@Tag("model")
interface ModelTest {

    @BeforeEach
    default void setUp(TestInfo testInfo) {
        System.out.println("Running Test - " + testInfo.getDisplayName());
    }
}
