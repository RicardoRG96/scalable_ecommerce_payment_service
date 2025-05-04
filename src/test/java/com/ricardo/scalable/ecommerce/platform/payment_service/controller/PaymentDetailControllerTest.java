package com.ricardo.scalable.ecommerce.platform.payment_service.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PaymentDetailControllerTest {

    @Autowired
    private Environment env;

    @Autowired
    private WebTestClient client;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void getPaymentDetailById_whenPaymentDetailExists_thenReturn200AndPaymentDetail() {
        client.get()
            .uri("/1")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertEquals(1, json.path("id").asInt()),
                        () -> assertEquals(15000, json.path("amount").asInt()),
                        () -> assertEquals("CLP", json.path("currency").asText()),
                        () -> assertEquals("WEBPAY", json.path("paymentMethod").asText()),
                        () -> assertEquals("FLOW", json.path("provider").asText()),
                        () -> assertEquals("TXN123789", json.path("transactionId").asText()),
                        () -> assertEquals("COMPLETED", json.path("status").asText())  
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(2)
    void getPaymentDetailById_whenPaymentDetailDoesNotExist_thenReturn404AndErrorMessage() {
        client.get()
            .uri("/9999")
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertEquals("Detalle de pago no encontrado", json.path("error").asText()),
                        () -> assertEquals("El detalle del pago no existe", json.path("message").asText()),
                        () -> assertEquals(404, json.path("status").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(3)
    void getPaymentDetailByOrderId_whenPaymentDetailExists_thenReturn200AndPaymentDetail() {
        client.get()
            .uri("/order/2")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertEquals(2, json.path("id").asInt()),
                        () -> assertEquals(89990, json.path("amount").asInt()),
                        () -> assertEquals("CLP", json.path("currency").asText()),
                        () -> assertEquals("WEBPAY", json.path("paymentMethod").asText()),
                        () -> assertEquals("FLOW", json.path("provider").asText()),
                        () -> assertEquals("TXN658541", json.path("transactionId").asText()),
                        () -> assertEquals("COMPLETED", json.path("status").asText())  
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(4)
    void getPaymentDetailByOrderId_whenPaymentDetailDoesNotExist_thenReturn404AndErrorMessage() {
        client.get()
            .uri("/order/9999")
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertEquals("Detalle de pago no encontrado", json.path("error").asText()),
                        () -> assertEquals("No existen detalles de pago para esta orden", json.path("message").asText()),
                        () -> assertEquals(404, json.path("status").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(5)
    void getPaymentDetailByCurrency_whenPaymentDetailExists_thenReturn200AndPaymentDetails() {
        client.get()
            .uri("/currency/CLP")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertTrue(json.isArray()),
                        () -> assertEquals(6, json.size()),
                        () -> assertEquals(1, json.get(0).path("id").asInt()),
                        () -> assertEquals(2, json.get(1).path("id").asInt()),
                        () -> assertEquals(3, json.get(2).path("id").asInt()),
                        () -> assertEquals(4, json.get(3).path("id").asInt()),
                        () -> assertEquals(5, json.get(4).path("id").asInt()),
                        () -> assertEquals(6, json.get(5).path("id").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(6)
    void getPaymentDetailByCurrency_whenPaymentDetailDoesNotExist_thenReturn404AndErrorMessage() {
        client.get()
            .uri("/currency/USD")
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertEquals("Detalle de pago no encontrado", json.path("error").asText()),
                        () -> assertEquals("No existen detalles de pago para esta moneda", json.path("message").asText()),
                        () -> assertEquals(404, json.path("status").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(7)
    void getPaymentDetailByProvider_whenPaymentDetailExists_thenReturn200AndPaymentDetails() {
        client.get()
            .uri("/provider/FLOW")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertTrue(json.isArray()),
                        () -> assertEquals(6, json.size()),
                        () -> assertEquals(1, json.get(0).path("id").asInt()),
                        () -> assertEquals(2, json.get(1).path("id").asInt()),
                        () -> assertEquals(3, json.get(2).path("id").asInt()),
                        () -> assertEquals(4, json.get(3).path("id").asInt()),
                        () -> assertEquals(5, json.get(4).path("id").asInt()),
                        () -> assertEquals(6, json.get(5).path("id").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(8)
    void getPaymentDetailByProvider_whenPaymentDetailDoesNotExist_thenReturn404AndErrorMessage() {
        client.get()
            .uri("/provider/XYZ")
            .exchange()
            .expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertNotNull(json),
                        () -> assertEquals("Detalle de pago no encontrado", json.path("error").asText()),
                        () -> assertEquals("No se encontraron registros con este proveedor de pago", json.path("message").asText()),
                        () -> assertEquals(404, json.path("status").asInt())
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    void testProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        assertArrayEquals(new String[] { "test" }, activeProfiles);
    }

    @Test
    void testApplicationPropertyFile() {
        assertEquals("jdbc:h2:mem:public;NON_KEYWORDS=value", env.getProperty("spring.datasource.url"));
    }

}
