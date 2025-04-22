package com.ricardo.scalable.ecommerce.platform.payment_service.gateway;

import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentRequest;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentResponse;

public interface PaymentGateway {

    PaymentResponse processPayment(PaymentRequest request);

    String getPaymentStatus(String paymentId);

}
