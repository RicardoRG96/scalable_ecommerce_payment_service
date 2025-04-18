package com.ricardo.scalable.ecommerce.platform.payment_service.exception;

public class FlowApiException extends RuntimeException {

    public FlowApiException(String message) {
        super(message);
    }

    public FlowApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlowApiException(Throwable cause) {
        super(cause);
    }

}
