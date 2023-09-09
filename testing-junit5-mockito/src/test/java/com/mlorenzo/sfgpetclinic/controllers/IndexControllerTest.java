package com.mlorenzo.sfgpetclinic.controllers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class IndexControllerTest implements ControllerTest {
    IndexController indexController;

    @BeforeEach
    void setUp() {
        indexController = new IndexController();
    }

    // En vez de mostrarse el nombre de este método en la ejecución de las pruebas(comportamiento por defecto),
    // podemos establecer un texto personalizado para mostrarlo)
    @DisplayName("Test proper view name is returned for index page")
    @Test
    void testIndex() {
        assertEquals("index", indexController.index());
        // Indicar un mensaje de error es opcional
        assertEquals("index", indexController.index(), "Wrong View Returned");
        // Usando una expresión lambda para la creación del mensaje, hacemos que esta creación sea perezosa("Lazy"),
        // es decir, sólo se creará si no se cumple esta condición. Útil si se tiene un proceso costoso para crear
        // el mensaje y, de esta forma, mejorar el rendimiento
        assertEquals("index", indexController.index(),
                () -> "Another expensive message. Make me only if you have to");
        // Afirmaciones o "Assertions" usando la librería "AssertJ"
        assertThat(indexController.index()).isEqualTo("index");
    }

    @Test
    @DisplayName("Test exception")
    void testOupsHandler() {
        assertThrows(ValueNotFoundException.class, () -> indexController.oupsHandler());
    }

    @Disabled("Demo of timeout")
    @Test
    void testTimeout() {
        // La tarea se ejecuta en el mismo hilo, así que la comprobación se hará después de que finalice esa tarea
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(2000);
            System.out.println("I got here");
        });
    }

    @Disabled("Demo of timeout")
    @Test
    void testTimeoutPremp() {
        // Tarda mucho menos que el caso anterior porque, con este método, la tarea se ejecuta en otro hilo. Entonces,
        // la comprobación se realiza tan pronto como haya transcurrido el tiempo de timeout establecido
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(2000);
            System.out.println("I got here 1234");
        });
    }

    // La diferencia entre "Assert" y "Assume" es que, con "Assert", el test se marca como fallido si no se cumple,
    // sin embargo, con "Assume", el test se ignora si no se cumple
    @Test
    void testAssumptionTrue() {
        assumeTrue("GURU".equalsIgnoreCase(System.getenv("GURU_RUNTIME")));
    }

    @Test
    void testAssumptionTrueAssumptionIsTrue() {
        assumeTrue("GURU".equalsIgnoreCase("guru"));
    }

    // **** Tests condiciones **** //
    // Se ejecutan si cumplen la condición. En caso contrario, se ignoran

    @EnabledOnOs(OS.MAC)
    @Test
    void testMeOnMacOS() {
    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void testMeOnWindows() {
    }

    @EnabledOnJre(JRE.JAVA_8)
    @Test
    void testMeOnJava8() {
    }

    @EnabledOnJre(JRE.JAVA_11)
    @Test
    void testMeOnJava11() {
    }

    @EnabledIfEnvironmentVariable(named = "USER", matches = "ml")
    @Test
    void testMeIfUserML() {
    }

    @EnabledIfEnvironmentVariable(named = "USER", matches = "fred")
    @Test
    void testMeIfUserFred() {
    }
}