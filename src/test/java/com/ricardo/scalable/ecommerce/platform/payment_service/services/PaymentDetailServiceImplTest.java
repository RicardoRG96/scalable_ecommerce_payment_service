package com.ricardo.scalable.ecommerce.platform.payment_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.payment_service.gateway.PaymentGateway;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.PaymentStatus;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.entities.PaymentDetail;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.OrderRepository;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.PaymentDetailRepository;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.PaymentDetailServiceImplTestData.*;

@SpringBootTest
public class PaymentDetailServiceImplTest {

    @MockitoBean
    private PaymentDetailRepository paymentDetailRepository;

    @MockitoBean
    private OrderRepository orderRepository;

    @MockitoBean
    private PaymentGateway paymentGateway;

    @Autowired
    private PaymentDetailService paymentDetailService;

    @Test
    void findById_whenPaymentDetailExists_thenReturnPaymentDetail() {
        when(paymentDetailRepository.findById(1L)).thenReturn(createPaymentDetail001());

        Optional<PaymentDetail> paymentDetail = paymentDetailService.findById(1L);

        assertAll(
            () -> assertTrue(paymentDetail.isPresent(), "PaymentDetail should be present"),
            () -> assertEquals(1L, paymentDetail.orElseThrow().getId(), "PaymentDetail ID should match"),
            () -> assertEquals("CLP", paymentDetail.orElseThrow().getCurrency(), "PaymentDetail currency should match"),
            () -> assertEquals("FLOW", paymentDetail.orElseThrow().getProvider(), "PaymentDetail provider should match"),
            () -> assertEquals("WEBPAY", paymentDetail.orElseThrow().getPaymentMethod(), "PaymentDetail payment method should match"),
            () -> assertEquals("TXN1234567890", paymentDetail.orElseThrow().getTransactionId(), "PaymentDetail transaction ID should match"),
            () -> assertEquals(PaymentStatus.fromCode(2), paymentDetail.orElseThrow().getStatus(), "PaymentDetail status should match")
        );
    }

    @Test
    void findById_whenPaymentDetailDoesNotExist_thenReturnEmpty() {
        when(paymentDetailRepository.findById(100L)).thenReturn(Optional.empty());

        Optional<PaymentDetail> paymentDetail = paymentDetailService.findById(100L);

        assertFalse(paymentDetail.isPresent(), "PaymentDetail should not be present");
    }

    @Test
    void findByOrderId_whenPaymentDetailExists_thenReturnPaymentDetail() {
        when(paymentDetailRepository.findByOrderId(1L)).thenReturn(createPaymentDetail001());

        Optional<PaymentDetail> paymentDetail = paymentDetailService.findByOrderId(1L);

        assertAll(
            () -> assertTrue(paymentDetail.isPresent(), "PaymentDetail should be present"),
            () -> assertEquals(1L, paymentDetail.orElseThrow().getId(), "PaymentDetail ID should match"),
            () -> assertEquals("CLP", paymentDetail.orElseThrow().getCurrency(), "PaymentDetail currency should match"),
            () -> assertEquals("FLOW", paymentDetail.orElseThrow().getProvider(), "PaymentDetail provider should match"),
            () -> assertEquals("WEBPAY", paymentDetail.orElseThrow().getPaymentMethod(), "PaymentDetail payment method should match"),
            () -> assertEquals("TXN1234567890", paymentDetail.orElseThrow().getTransactionId(), "PaymentDetail transaction ID should match"),
            () -> assertEquals(PaymentStatus.fromCode(2), paymentDetail.orElseThrow().getStatus(), "PaymentDetail status should match")
        );
    }

    @Test
    void findByOrderId_whenPaymentDetailDoesNotExist_thenReturnEmpty() {
        when(paymentDetailRepository.findByOrderId(100L)).thenReturn(Optional.empty());

        Optional<PaymentDetail> paymentDetail = paymentDetailService.findByOrderId(100L);

        assertFalse(paymentDetail.isPresent(), "PaymentDetail should not be present");
    }

    @Test
    void findByCurrency_whenPaymentDetailExists_thenReturnPaymentDetail() {
        when(paymentDetailRepository.findByCurrency("CLP")).thenReturn(Optional.of(createListOfPaymentDetails()));

        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByCurrency("CLP");

        assertAll(
            () -> assertTrue(paymentDetail.isPresent(), "PaymentDetail should be present"),
            () -> assertEquals(5, paymentDetail.orElseThrow().size()),
            () -> assertEquals(1L, paymentDetail.orElseThrow().get(0).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(2L, paymentDetail.orElseThrow().get(1).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(3L, paymentDetail.orElseThrow().get(2).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(4L, paymentDetail.orElseThrow().get(3).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(5L, paymentDetail.orElseThrow().get(4).getId(), "PaymentDetail ID should match")
        );
    }

    @Test
    void findByCurrency_whenPaymentDetailDoesNotExist_thenReturnEmpty() {
        when(paymentDetailRepository.findByCurrency("USD")).thenReturn(Optional.empty());

        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByCurrency("USD");

        assertFalse(paymentDetail.isPresent(), "PaymentDetail should not be present");
    }

}
