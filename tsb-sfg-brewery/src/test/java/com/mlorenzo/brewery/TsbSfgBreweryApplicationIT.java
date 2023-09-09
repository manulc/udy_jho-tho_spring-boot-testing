package com.mlorenzo.brewery;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Esta anotación levanta t?odo el contexto de Spring y, por defecto, crea un Mock del API Servlet sin levantar
// el servidor embebido. Esta anotación ya incluye la anotación "@ExtendWith(SpringExtension.class)".
@SpringBootTest
class TsbSfgBreweryApplicationIT {

    @Test
    void contextLoads() {
    }

}
