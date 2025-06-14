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

import com.ricardo.scalable.ecommerce.platform.payment_service.exception.OrderAlreadyPaidException;
import com.ricardo.scalable.ecommerce.platform.payment_service.gateway.PaymentGateway;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.PaymentStatus;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.entities.PaymentDetail;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.OrderRepository;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.PaymentDetailRepository;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.PaymentDetailServiceImplTestData.*;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.OrderTestData.*;

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
            () -> assertEquals(6, paymentDetail.orElseThrow().size()),
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

    @Test
    void findByProvider_whenPaymentDetailExists_thenReturnPaymentDetail() {
        when(paymentDetailRepository.findByProvider("FLOW")).thenReturn(Optional.of(createListOfPaymentDetails()));

        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByProvider("FLOW");

        assertAll(
            () -> assertTrue(paymentDetail.isPresent(), "PaymentDetail should be present"),
            () -> assertEquals(6, paymentDetail.orElseThrow().size()),
            () -> assertEquals(1L, paymentDetail.orElseThrow().get(0).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(2L, paymentDetail.orElseThrow().get(1).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(3L, paymentDetail.orElseThrow().get(2).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(4L, paymentDetail.orElseThrow().get(3).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(5L, paymentDetail.orElseThrow().get(4).getId(), "PaymentDetail ID should match")
        );
    }

    @Test
    void findByProvider_whenPaymentDetailDoesNotExist_thenReturnEmpty() {
        when(paymentDetailRepository.findByProvider("STRIPE")).thenReturn(Optional.empty());

        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByProvider("STRIPE");

        assertFalse(paymentDetail.isPresent(), "PaymentDetail should not be present");
    }

    @Test
    void findByPaymentMethod_whenPaymentDetailExists_thenReturnPaymentDetail() {
        when(paymentDetailRepository.findByPaymentMethod("WEBPAY")).thenReturn(createListOfPaymentDetailByPaymentMethod());

        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByPaymentMethod("WEBPAY");

        assertAll(
            () -> assertTrue(paymentDetail.isPresent(), "PaymentDetail should be present"),
            () -> assertEquals(2, paymentDetail.orElseThrow().size()),
            () -> assertEquals(1L, paymentDetail.orElseThrow().get(0).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(2L, paymentDetail.orElseThrow().get(1).getId(), "PaymentDetail ID should match"),
            () -> assertEquals("TXN1234567890", paymentDetail.orElseThrow().get(0).getTransactionId(),
                "PaymentDetail transaction ID should match"),
            () -> assertEquals("TXN0987654321", paymentDetail.orElseThrow().get(1).getTransactionId(),
                 "PaymentDetail transaction ID should match")
        );
    }

    @Test
    void findByPaymentMethod_whenPaymentDetailDoesNotExist_thenReturnEmpty() {
        when(paymentDetailRepository.findByPaymentMethod("CREDIT_CARD")).thenReturn(Optional.empty());

        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByPaymentMethod("CREDIT_CARD");

        assertFalse(paymentDetail.isPresent(), "PaymentDetail should not be present");
    }

    @Test
    void findByTransactionId_whenPaymentDetailExists_thenReturnPaymentDetail() {
        when(paymentDetailRepository.findByTransactionId("TXN1234567890")).thenReturn(createPaymentDetail001());

        Optional<PaymentDetail> paymentDetail = paymentDetailService.findByTransactionId("TXN1234567890");

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
    void findByTransactionId_whenPaymentDetailDoesNotExist_thenReturnEmpty() {
        when(paymentDetailRepository.findByTransactionId("TXN0000000000")).thenReturn(Optional.empty());

        Optional<PaymentDetail> paymentDetail = paymentDetailService.findByTransactionId("TXN0000000000");

        assertFalse(paymentDetail.isPresent(), "PaymentDetail should not be present");
    }

    @Test
    void findByStatus_whenPaymentDetailExists_thenReturnPaymentDetail() {
        when(paymentDetailRepository.findByStatus(PaymentStatus.fromDescription("COMPLETED"))).thenReturn(createListOfPaymentDetailByPaymentStatus());

        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByStatus("COMPLETED");

        assertAll(
            () -> assertTrue(paymentDetail.isPresent(), "PaymentDetail should be present"),
            () -> assertEquals(3, paymentDetail.orElseThrow().size()),
            () -> assertEquals(1L, paymentDetail.orElseThrow().get(0).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(2L, paymentDetail.orElseThrow().get(1).getId(), "PaymentDetail ID should match"),
            () -> assertEquals(3L, paymentDetail.orElseThrow().get(2).getId(), "PaymentDetail ID should match")
        );
    }

    @Test
    void findByStatus_whenPaymentDetailDoesNotExist_thenReturnEmpty() {
        when(paymentDetailRepository.findByStatus(PaymentStatus.fromDescription("PENDING"))).thenReturn(Optional.empty());

        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByStatus("PENDING");

        assertFalse(paymentDetail.isPresent(), "PaymentDetail should not be present");
    }

    @Test
    void findAll_whenPaymentDetailExists_thenReturnAllPaymentDetails() {
        when(paymentDetailRepository.findAll()).thenReturn(createListOfPaymentDetails());

        List<PaymentDetail> paymentDetails = paymentDetailService.findAll();

        assertEquals(6, paymentDetails.size(), "Should return all PaymentDetails");
    }

    @Test
    void findAll_whenNoPaymentDetailExists_thenReturnEmptyList() {
        when(paymentDetailRepository.findAll()).thenReturn(List.of());

        List<PaymentDetail> paymentDetails = paymentDetailService.findAll();

        assertTrue(paymentDetails.isEmpty(), "Should return empty list of PaymentDetails");
    }

    @Test
    void createPaymentAndGetRedirectUrl_whenPaymentDetailExists_thenReturnRedirectUrl() {
        when(paymentDetailRepository.findByOrderId(7L)).thenReturn(Optional.empty());
        when(orderRepository.findById(7L)).thenReturn(createOrder007());
        when(paymentDetailRepository.findFirstByOrderIdAndStatusIn(7L, List.of(PaymentStatus.PENDING, PaymentStatus.FAILED)))
            .thenReturn(Optional.of(new PaymentDetail()));
        when(paymentGateway.processPayment(any())).thenReturn(createPaymentResponse());
        when(paymentDetailRepository.save(any(PaymentDetail.class))).thenReturn(getPaymentDetailCreated().orElseThrow());

        Optional<String> redirectUrl = paymentDetailService.createPaymentAndGetRedirectUrl(createPaymentRequest());

        assertAll(
            () -> assertTrue(redirectUrl.isPresent(), "Redirect URL should be present"),
            () -> assertEquals("https://payment-link.com/transaction?token=TXN1234567890", redirectUrl.orElseThrow(), 
                "Redirect URL should match")
        );
    }

    @Test
    void createPaymentAndGetRedirectUrl_whenOrderDoesNotExist_thenReturnEmpty() {
        when(paymentDetailRepository.findByOrderId(100L)).thenReturn(Optional.empty());
        when(orderRepository.findById(100L)).thenReturn(Optional.empty());

        Optional<String> redirectUrl = paymentDetailService.createPaymentAndGetRedirectUrl(createPaymentRequest());

        assertFalse(redirectUrl.isPresent(), "Redirect URL should not be present");
    }

    @Test
    void createPaymentAndGetRedirectUrl_whenOrderIsAlreadyPaid_thenThrowException() {
        when(paymentDetailRepository.findByOrderId(1L)).thenReturn(createPaymentDetail001());

        assertThrows(
            OrderAlreadyPaidException.class, 
            () -> paymentDetailService.createPaymentAndGetRedirectUrl(createPaymentRequestWithOrderAlreadyPaid())
        );
    }

    @Test
    void createPaymentAndGetRedirectUrl_whenOrderIsRefunded_thenThrowException() {
        when(paymentDetailRepository.findByOrderId(6L)).thenReturn(createPaymentDetail006());

        assertThrows(
            OrderAlreadyPaidException.class, 
            () -> paymentDetailService.createPaymentAndGetRedirectUrl(createPaymentRequestWithOrderRefunded())
        );
    }

    @Test
    void createPaymentAndGetRedirectUrl_whenOrderHasFailedPayment_thenReturnRedirectUrl() {
        when(paymentDetailRepository.findByOrderId(4L)).thenReturn(createPaymentDetail004());
        when(orderRepository.findById(4L)).thenReturn(createOrder004());
        when(paymentDetailRepository.findFirstByOrderIdAndStatusIn(4L, List.of(PaymentStatus.PENDING, PaymentStatus.FAILED)))
            .thenReturn(createPaymentDetail004());
        when(paymentGateway.processPayment(any())).thenReturn(createPaymentResponseWithOrderWithFailedPayment());
        when(paymentDetailRepository.save(any()))
            .thenReturn(getPaymentDetailCreatedWithOrderWithFailedPayment().orElseThrow());

        Optional<String> redirectUrl = 
                paymentDetailService.createPaymentAndGetRedirectUrl(createPaymentRequestWithOrderWithFailedPayment());

        assertAll(
            () -> assertTrue(redirectUrl.isPresent(), "Redirect URL should be present"),
            () -> assertEquals("https://payment-link.com/transaction?token=TXN4567891230", redirectUrl.orElseThrow(), 
                "Redirect URL should match")
        );
    }

    @Test
    void confirmPayment_whenPaymentDetailExists_thenUpdatePaymentDetail() {
        when(paymentDetailRepository.findByTransactionId("TXN1234567890")).thenReturn(createPaymentDetail001());
        when(paymentGateway.getPaymentStatus("TXN1234567890")).thenReturn("COMPLETED");
        when(paymentGateway.getPaymentMethod("TXN1234567890")).thenReturn("WEBPAY");

        paymentDetailService.confirmPayment("TXN1234567890");

        verify(paymentDetailRepository, times(1)).save(any(PaymentDetail.class));
    }

    @Test
    void delete_whenPaymentDetailExists_thenDeletePaymentDetail() {
        when(paymentDetailRepository.findById(1L)).thenReturn(createPaymentDetail001());

        paymentDetailService.delete(1L);

        verify(paymentDetailRepository, times(1)).deleteById(1L);
    }

}
