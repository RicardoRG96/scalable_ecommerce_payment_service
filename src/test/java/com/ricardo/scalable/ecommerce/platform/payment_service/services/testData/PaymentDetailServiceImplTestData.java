package com.ricardo.scalable.ecommerce.platform.payment_service.services.testData;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Order;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.PaymentStatus;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentRequest;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentResponse;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.entities.PaymentDetail;

import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.OrderTestData.*;

public class PaymentDetailServiceImplTestData {

    public static List<PaymentDetail> createListOfPaymentDetails() {
        PaymentDetail paymentDetail1 = createPaymentDetail001().orElseThrow();
        PaymentDetail paymentDetail2 = createPaymentDetail002().orElseThrow();
        PaymentDetail paymentDetail3 = createPaymentDetail003().orElseThrow();
        PaymentDetail paymentDetail4 = createPaymentDetail004().orElseThrow();
        PaymentDetail paymentDetail5 = createPaymentDetail005().orElseThrow();

        return List.of(paymentDetail1, paymentDetail2, paymentDetail3, paymentDetail4, paymentDetail5);
    }

    public static Optional<PaymentDetail> createPaymentDetail001() {
        PaymentDetail paymentDetail = new PaymentDetail();
        Order order = createOrder001().orElseThrow();

        paymentDetail.setId(1L);
        paymentDetail.setOrder(order);
        paymentDetail.setAmount(new BigDecimal("199.99"));
        paymentDetail.setCurrency("CLP");
        paymentDetail.setProvider("FLOW");
        paymentDetail.setPaymentMethod("WEBPAY");
        paymentDetail.setTransactionId("TXN1234567890");
        paymentDetail.setStatus(PaymentStatus.fromCode(2));
        paymentDetail.setCreatedAt(Timestamp.from(Instant.now()));
        paymentDetail.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(paymentDetail);
    }

    public static Optional<PaymentDetail> createPaymentDetail002() {
        PaymentDetail paymentDetail = new PaymentDetail();
        Order order = createOrder002().orElseThrow();

        paymentDetail.setId(2L);
        paymentDetail.setOrder(order);
        paymentDetail.setAmount(new BigDecimal("49.99"));
        paymentDetail.setCurrency("CLP");
        paymentDetail.setProvider("FLOW");
        paymentDetail.setPaymentMethod("WEBPAY");
        paymentDetail.setTransactionId("TXN0987654321");
        paymentDetail.setStatus(PaymentStatus.fromCode(2));
        paymentDetail.setCreatedAt(Timestamp.from(Instant.now()));
        paymentDetail.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(paymentDetail);
    }

    public static Optional<PaymentDetail> createPaymentDetail003() {
        PaymentDetail paymentDetail = new PaymentDetail();
        Order order = createOrder003().orElseThrow();

        paymentDetail.setId(3L);
        paymentDetail.setOrder(order);
        paymentDetail.setAmount(new BigDecimal("89.99"));
        paymentDetail.setCurrency("CLP");
        paymentDetail.setProvider("FLOW");
        paymentDetail.setPaymentMethod("MACH");
        paymentDetail.setTransactionId("TXN1122334455");
        paymentDetail.setStatus(PaymentStatus.fromCode(2));
        paymentDetail.setCreatedAt(Timestamp.from(Instant.now()));
        paymentDetail.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(paymentDetail);
    }

    public static Optional<PaymentDetail> createPaymentDetail004() {
        PaymentDetail paymentDetail = new PaymentDetail();
        Order order = createOrder004().orElseThrow();

        paymentDetail.setId(4L);
        paymentDetail.setOrder(order);
        paymentDetail.setAmount(new BigDecimal("39.99"));
        paymentDetail.setCurrency("CLP");
        paymentDetail.setProvider("FLOW");
        paymentDetail.setPaymentMethod("MACH");
        paymentDetail.setTransactionId("TXN5566778899");
        paymentDetail.setStatus(PaymentStatus.fromCode(3));
        paymentDetail.setCreatedAt(Timestamp.from(Instant.now()));
        paymentDetail.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(paymentDetail);
    }

    public static Optional<PaymentDetail> createPaymentDetail005() {
        PaymentDetail paymentDetail = new PaymentDetail();
        Order order = createOrder001().orElseThrow();

        paymentDetail.setId(5L);
        paymentDetail.setOrder(order);
        paymentDetail.setAmount(new BigDecimal("99.99"));
        paymentDetail.setCurrency("CLP");
        paymentDetail.setProvider("FLOW");
        paymentDetail.setPaymentMethod("PAYPAL");
        paymentDetail.setTransactionId("TXN1234567890");
        paymentDetail.setStatus(PaymentStatus.fromCode(1));
        paymentDetail.setCreatedAt(Timestamp.from(Instant.now()));
        paymentDetail.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(paymentDetail);
    }

    public static Optional<List<PaymentDetail>> createListOfPaymentDetailByPaymentMethod() {
        PaymentDetail paymentDetail1 = createPaymentDetail001().orElseThrow();
        PaymentDetail paymentDetail2 = createPaymentDetail002().orElseThrow();

        return Optional.of(List.of(paymentDetail1, paymentDetail2));
    }


    public static Optional<List<PaymentDetail>> createListOfPaymentDetailByPaymentStatus() {
        PaymentDetail paymentDetail1 = createPaymentDetail001().orElseThrow();
        PaymentDetail paymentDetail2 = createPaymentDetail002().orElseThrow();
        PaymentDetail paymentDetail3 = createPaymentDetail003().orElseThrow();

        return Optional.of(List.of(paymentDetail1, paymentDetail2, paymentDetail3));
    }

    public static PaymentRequest createPaymentRequest() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(6L);
        paymentRequest.setAmount(new BigDecimal("149.99"));
        paymentRequest.setCurrency("CLP");
        paymentRequest.setEmail("carla@gmail.com");

        return paymentRequest;
    }

    public static PaymentResponse createPaymentResponse() {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setTransactionId("TXN1234567890");
        paymentResponse.setStatus("PENDING");
        paymentResponse.setProvider("FLOW");
        paymentResponse.setMessage("Pago generado");
        paymentResponse.setPaymentLink("https://payment-link.com/transaction?token=TXN1234567890");

        return paymentResponse;
    }

    public static Optional<PaymentDetail> getPaymentDetailCreated() {
        PaymentDetail paymentDetail = new PaymentDetail();
        Order order = createOrder006().orElseThrow();

        paymentDetail.setId(6L);
        paymentDetail.setOrder(order);
        paymentDetail.setAmount(new BigDecimal("149.99"));
        paymentDetail.setCurrency("CLP");
        paymentDetail.setProvider("FLOW");
        paymentDetail.setPaymentMethod(null);
        paymentDetail.setTransactionId("TXN1234567890");
        paymentDetail.setStatus(PaymentStatus.fromCode(1));
        paymentDetail.setCreatedAt(Timestamp.from(Instant.now()));
        paymentDetail.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(paymentDetail);
    }
}
