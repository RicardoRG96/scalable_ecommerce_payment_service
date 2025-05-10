package com.ricardo.scalable.ecommerce.platform.payment_service.exception;

public class PaymentNotConfirmedException extends RuntimeException {

    public PaymentNotConfirmedException(String message) {
        super(message);
    }

    public PaymentNotConfirmedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentNotConfirmedException(Throwable cause) {
        super(cause);
    }

}
