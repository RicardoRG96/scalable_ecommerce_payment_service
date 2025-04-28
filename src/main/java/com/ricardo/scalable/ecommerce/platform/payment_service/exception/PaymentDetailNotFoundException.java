package com.ricardo.scalable.ecommerce.platform.payment_service.exception;

public class PaymentDetailNotFoundException extends RuntimeException {
    public PaymentDetailNotFoundException(String message) {
        super(message);
    }

    public PaymentDetailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
