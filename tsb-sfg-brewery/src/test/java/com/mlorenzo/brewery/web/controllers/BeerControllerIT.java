package com.mlorenzo.brewery.web.controllers;

import com.mlorenzo.brewery.web.model.BeerPagedList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.*;

// En este caso, como estamos indicando al atributo "webEnvironment" de esta anotación el valor "RANDOM_PORT",
// además de levantar t?odo el contexto de Spring, también arrancará el servidor web embebido en un puerto aleatorio
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerControllerIT {

    // El puerto aleatorio utilizado por el servidor web embebido es configurado automáticamente en este
    // cliente http de tipo "TestRestTemplate" por Spring Boot
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testListBeers() {
        BeerPagedList beerPagedList = testRestTemplate.getForObject("/api/v1/beer", BeerPagedList.class);
        assertThat(beerPagedList.getContent()).hasSize(3);
    }
}
