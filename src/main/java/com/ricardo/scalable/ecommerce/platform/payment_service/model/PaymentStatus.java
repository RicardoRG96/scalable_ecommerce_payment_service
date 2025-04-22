package com.ricardo.scalable.ecommerce.platform.payment_service.model;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;
import com.ricardo.scalable.ecommerce.platform.payment_service.exception.UnsupportedFlowStatusCodeException;

public enum PaymentStatus {
    
    PENDING(1, "PENDING"),
    COMPLETED(2, "COMPLETED"),
    FAILED(3, "FAILED"),
    REFUNDED(4, "REFUNDED");

    private final int code;

    private final String description;

    private static final Map<Integer, PaymentStatus> CODE_MAP =
            Stream.of(values()).collect(Collectors.toMap(PaymentStatus::getCode, e -> e));

    PaymentStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() { 
        return code; 
    }

    public String getDescription() { 
        return description; 
    }

    public static PaymentStatus fromCode(int code) {
        return Optional.ofNullable(CODE_MAP.get(code))
            .orElseThrow(() -> new UnsupportedFlowStatusCodeException(code));
    }

}
