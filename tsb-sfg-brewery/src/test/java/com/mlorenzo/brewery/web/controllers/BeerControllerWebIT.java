package com.mlorenzo.brewery.web.controllers;

import com.mlorenzo.brewery.services.BeerService;
import com.mlorenzo.brewery.web.model.BeerDto;
import com.mlorenzo.brewery.web.model.BeerPagedList;
import com.mlorenzo.brewery.web.model.BeerStyleEnum;
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

import java.math.BigDecimal;
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
// En este caso en concreto, sólo levanta el contexto de Spring para el controlador "BeerController"
@WebMvcTest(BeerController.class)
class BeerControllerWebIT {

    // Esta anotación sustituye a la anotación "@Mock" cuando usamos Mockito directamente, es decir, es una anotación
    // envoltorio de la anotación "@Mock" para usarla junto con la anotación  "@WebMvcTest"
    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    List<BeerDto> validBeers;

    @BeforeEach
    void setUp() {
        BeerDto validBeer1 = BeerDto.builder()
                .version(1)
                .id(UUID.randomUUID())
                .beerName("beer1")
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .price(new BigDecimal("12.99"))
                .quantityOnHand(4)
                .upc(123456789012L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        BeerDto validBeer2 = BeerDto.builder()
                .version(1)
                .id(UUID.randomUUID())
                .beerName("beer2")
                .beerStyle(BeerStyleEnum.IPA)
                .price(new BigDecimal("10.99"))
                .quantityOnHand(2)
                .upc(123456789013L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
        validBeers = new ArrayList<>();
        validBeers.add(validBeer1);
        validBeers.add(validBeer2);
    }

    @Test
    void getBeerByIdBDDTest() throws Exception {
        // Given
        BeerDto validBeer = validBeers.get(0);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        given(beerService.findBeerById(any(UUID.class))).willReturn(validBeer);
        // When
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/beer/{beerId}", validBeer.getId()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(validBeer.getBeerName())))
                .andExpect(jsonPath("$.createdDate",
                        is(dateTimeFormatter.format(validBeer.getCreatedDate()))))
                .andReturn();
        then(beerService).should(times(1)).findBeerById(any(UUID.class));
        then(beerService).shouldHaveNoMoreInteractions();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void listOfBeersBDDTest() throws Exception {
        // Given
        ArgumentCaptor<PageRequest> pageRequestArgumentCaptor = ArgumentCaptor.forClass(PageRequest.class);
        given(beerService.listBeers(eq(null), eq(null), pageRequestArgumentCaptor.capture()))
                .willReturn(new BeerPagedList(validBeers));
        // When
        mockMvc.perform(get("/api/v1/beer"))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(validBeers.get(0).getId().toString())));
        PageRequest capturedPageRequest = pageRequestArgumentCaptor.getValue();
        assertThat(capturedPageRequest.getPageNumber()).isEqualTo(0);
        assertThat(capturedPageRequest.getPageSize()).isEqualTo(25);
        then(beerService).should().listBeers(null, null, capturedPageRequest);
        then(beerService).shouldHaveNoMoreInteractions();
    }
}