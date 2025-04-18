package com.ricardo.scalable.ecommerce.platform.payment_service.exception;

public class UnsupportedFlowStatusCodeException extends RuntimeException {

    public UnsupportedFlowStatusCodeException(int code) {
        super("Código de estado FLOW no soportado: " + code);
    }

}
