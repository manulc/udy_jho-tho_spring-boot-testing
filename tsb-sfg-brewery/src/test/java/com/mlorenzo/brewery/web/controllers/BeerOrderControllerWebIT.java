package com.mlorenzo.brewery.web.controllers;

import com.mlorenzo.brewery.services.BeerOrderService;
import com.mlorenzo.brewery.web.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Esta anotación levanta el contexto de Spring correspondiente a la parte web, es decir, los controladores
// En este caso en concreto, sólo levanta el contexto de Spring para el controlador "BeerOrderController"
@WebMvcTest(BeerOrderController.class)
class BeerOrderControllerWebIT {

    // Esta anotación sustituye a la anotación "@Mock" cuando usamos Mockito directamente, es decir, es una anotación
    // envoltorio de la anotación "@Mock" para usarla junto con la anotación  "@WebMvcTest"
    @MockBean
    BeerOrderService beerOrderService;

    @Autowired
    MockMvc mockMvc;

    List<BeerOrderDto> validBeerOrders;
    UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        BeerOrderDto validBeerOrder1 = BeerOrderDto.builder()
                .version(1)
                .id(UUID.randomUUID())
                .customerId(customerId)
                .customerRef("abc")
                .orderStatus(OrderStatusEnum.NEW)
                .orderStatusCallbackUrl("http://abc")
                .beerOrderLines(List.of())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        BeerOrderDto validBeerOrder2 = BeerOrderDto.builder()
                .version(1)
                .id(UUID.randomUUID())
                .customerId(customerId)
                .customerRef("efg")
                .orderStatus(OrderStatusEnum.NEW)
                .orderStatusCallbackUrl("http://abc")
                .beerOrderLines(List.of())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        validBeerOrders = new ArrayList<>();
        validBeerOrders.add(validBeerOrder1);
        validBeerOrders.add(validBeerOrder2);
    }

    @Test
    void getBeerOrderByIdBDDTest() throws Exception {
        // Given
        BeerOrderDto validBeerOrder = validBeerOrders.get(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        given(beerOrderService.getOrderById(any(UUID.class), any(UUID.class))).willReturn(validBeerOrder);
        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/customers/{customerId}/orders/{orderId}",
                        customerId, validBeerOrder.getId()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(validBeerOrder.getId().toString())))
                .andExpect(jsonPath("$.customerId", is(validBeerOrder.getCustomerId().toString())))
                .andExpect(jsonPath("$.customerRef", is(validBeerOrder.getCustomerRef())))
                .andExpect(jsonPath("$.createdDate",
                        is(dateTimeFormatter.format(validBeerOrder.getCreatedDate()))))
                .andReturn();
        then(beerOrderService).should(times(1)).getOrderById(any(UUID.class), any(UUID.class));
        then(beerOrderService).shouldHaveNoMoreInteractions();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void listOfBeerOrdersBDDTest() throws Exception {
        // Given
        ArgumentCaptor<PageRequest> pageRequestArgumentCaptor = ArgumentCaptor.forClass(PageRequest.class);
        given(beerOrderService.listOrders(eq(customerId), pageRequestArgumentCaptor.capture()))
                .willReturn(new BeerOrderPagedList(validBeerOrders));
        // When
        mockMvc.perform(get("/api/v1/customers/{customerId}/orders", customerId))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(validBeerOrders.get(0).getId().toString())));
        PageRequest capturedPageRequest = pageRequestArgumentCaptor.getValue();
        assertThat(capturedPageRequest.getPageNumber()).isEqualTo(0);
        assertThat(capturedPageRequest.getPageSize()).isEqualTo(25);
        then(beerOrderService).should().listOrders(customerId, capturedPageRequest);
        then(beerOrderService).shouldHaveNoMoreInteractions();
    }
}