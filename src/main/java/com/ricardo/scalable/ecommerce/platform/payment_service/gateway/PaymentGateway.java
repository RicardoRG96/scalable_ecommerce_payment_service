package com.ricardo.scalable.ecommerce.platform.payment_service.gateway;

import com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto.PaymentRequest;
import com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto.PaymentResponse;

public interface PaymentGateway {

    PaymentResponse processPayment(PaymentRequest request);

}
