package com.mlorenzo.sfgpetclinic.model;

import com.mlorenzo.sfgpetclinic.CustomArgsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class OwnerTest implements ModelTest {

    @Test
    void dependentAssertionsTest() {
        Owner owner = new Owner(1L, "Joe", "Buck");
        owner.setCity("Key West");
        owner.setTelephone("123123123");
        assertAll("Properties Test",
                () -> assertAll("Person Properties",
                        () -> assertEquals("Joe", owner.getFirstName()),
                        () -> assertEquals("Buck", owner.getLastName())),
                () -> assertAll("Owner Properties",
                        () -> assertEquals("Key West", owner.getCity()),
                        () -> assertEquals("123123123", owner.getTelephone()))
        );
        // Afirmaciones o "Assertions" usando la librería "Hamcrest"
        assertThat(owner.getCity(), is("Key West"));
    }

    // Este test se repite tantas veces como valores haya en la anotación "@ValueSource"
    // A cada repetición se le pasa un valor
    @DisplayName("Value Source Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ValueSource(strings = { "Spring", "Framework", "Guru" })
    void testValueSource(String val) {
        System.out.println(val);
    }

    // Este test se repite tantas veces como valores haya en la enumeración "OwnerType"
    // A cada repetición se le pasa un valor
    @DisplayName("Enum Source Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @EnumSource(OwnerType.class)
    void testEnum(OwnerType ownerType) {
        System.out.println(ownerType);
    }

    // Este test se repite tantas veces como líneas haya en el CSV establecido en la anotación "@CsvSource"
    // A cada repetición se le pasa los valores de una línea
    @DisplayName("CSV Input Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @CsvSource({
            "FL, 1, 1",
            "OH, 2, 2",
            "MI, 1, 1"
    })
    void testCSVInput(String stateName, int val1, int val2) {
        System.out.println(stateName + " = " + val1 + ":" + val2);
    }

    // Este test se repite tantas veces como líneas haya en el fichero CSV indicado en la anotación "@CsvFileSource"
    // A cada repetición se le pasa los valores de una línea
    @DisplayName("CSV From File Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @CsvFileSource(resources = "/input.csv", numLinesToSkip = 1) // Se salta la línea 1 correspondiente a la cabecera
    void testCSVFromFile(String stateName, int val1, int val2) {
        System.out.println(stateName + " = " + val1 + ":" + val2);
    }

    // Este test se repite tantas veces como argumentos devuelva el método indicado en la anotación "@MethodSource"
    // A cada repetición se le pasa los valores de un argumento
    @DisplayName("Method Provider Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @MethodSource("getArgs")
    void testFromMethod(String stateName, int val1, int val2) {
        System.out.println(stateName + " = " + val1 + ":" + val2);
    }

    static Stream<Arguments> getArgs() {
        return Stream.of(
                Arguments.of("FL", 5, 1),
                Arguments.of("OH", 2, 8),
                Arguments.of("MI", 3, 5));
    }

    // Este test se repite tantas veces como argumentos devuelva la implementación de la interfaz "ArgumentsProvider"
    // En este caso, dicha implementación la realiza la clase "CustomArgsProvider"
    // A cada repetición se le pasa los valores de un argumento
    @DisplayName("Custom Provider Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ArgumentsSource(CustomArgsProvider.class)
    void testFromCustomProvider(String stateName, int val1, int val2) {
        System.out.println(stateName + " = " + val1 + ":" + val2);
    }
}