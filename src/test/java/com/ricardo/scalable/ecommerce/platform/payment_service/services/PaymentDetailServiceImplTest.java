package com.ricardo.scalable.ecommerce.platform.payment_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

}
