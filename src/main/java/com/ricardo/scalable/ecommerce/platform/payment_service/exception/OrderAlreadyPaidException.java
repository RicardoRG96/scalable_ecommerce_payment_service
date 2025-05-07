package com.ricardo.scalable.ecommerce.platform.payment_service.exception;

public class OrderAlreadyPaidException extends RuntimeException {

    public OrderAlreadyPaidException(String message) {
        super(message);
    }

    public OrderAlreadyPaidException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderAlreadyPaidException(Throwable cause) {
        super(cause);
    }

}
