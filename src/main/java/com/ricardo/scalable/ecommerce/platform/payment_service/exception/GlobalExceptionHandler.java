package com.ricardo.scalable.ecommerce.platform.payment_service.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FlowApiException.class)
    public ResponseEntity<Map<String, Object>> handleFlowApiException(FlowApiException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", ex.getMessage());
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorBody);
    }

}
