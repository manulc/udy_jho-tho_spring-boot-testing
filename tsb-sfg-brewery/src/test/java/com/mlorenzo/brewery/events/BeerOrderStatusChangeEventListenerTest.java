package com.mlorenzo.brewery.events;

import com.github.jenspiegsa.wiremockextension.Managed;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.mlorenzo.brewery.domain.BeerOrder;
import com.mlorenzo.brewery.domain.OrderStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ExtendWith(WireMockExtension.class)
class BeerOrderStatusChangeEventListenerTest {

    @Managed
    WireMockServer wireMockServer = with(wireMockConfig().dynamicPort());

    BeerOrderStatusChangeEventListener listener;

    @BeforeEach
    void setUp() {
        listener = new BeerOrderStatusChangeEventListener(new RestTemplateBuilder());
    }

    @Test
    void listenTest() {
        BeerOrder beerOrder = BeerOrder.builder()
                .orderStatus(OrderStatusEnum.READY)
                .orderStatusCallbackUrl("http://localhost:" + wireMockServer.port() + "/update")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        wireMockServer.stubFor(post("/update").willReturn(ok()));
        BeerOrderStatusChangeEvent event = new BeerOrderStatusChangeEvent(beerOrder, OrderStatusEnum.NEW);
        listener.listen(event);
        verify(1, postRequestedFor(urlEqualTo("/update")));
    }
}