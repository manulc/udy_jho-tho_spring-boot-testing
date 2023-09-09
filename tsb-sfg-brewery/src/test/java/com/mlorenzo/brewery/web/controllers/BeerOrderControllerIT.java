package com.mlorenzo.brewery.web.controllers;

import com.mlorenzo.brewery.domain.Customer;
import com.mlorenzo.brewery.repositories.CustomerRepository;
import com.mlorenzo.brewery.web.model.BeerOrderPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

// En este caso, como estamos indicando al atributo "webEnvironment" de esta anotación el valor "RANDOM_PORT",
// además de levantar t?odo el contexto de Spring, también arrancará el servidor web embebido en un puerto aleatorio
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerOrderControllerIT {

    // El puerto aleatorio utilizado por el servidor web embebido es configurado automáticamente en este
    // cliente http de tipo "TestRestTemplate" por Spring Boot
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
    }

    @Test
    void testListBeerOrders() {
        BeerOrderPagedList beerOrderPagedList =testRestTemplate.getForObject("/api/v1/customers/{customerId}/orders",
                BeerOrderPagedList.class, customer.getId().toString());
        assertThat(beerOrderPagedList.getContent()).hasSize(1);
    }
}
