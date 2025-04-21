package com.ricardo.scalable.ecommerce.platform.payment_service.model.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FlowPaymentStatusResponse {

    private long flowOrder;

    private String commerceOrder;

    private String requestDate;

    private int status;

    private String subject;

    private String currency;

    private BigDecimal amount;

    private String payer;

    private OptionalInfo optional;

    private PendingInfo pending_info;

    private PaymentData paymentData;

    private String merchantId;

    @Data
    public static class OptionalInfo {

        private String RUT;

        private String ID;

    }

    @Data
    public static class PendingInfo {

        private String media;

        private String date;

    }

    @Data
    public static class PaymentData {

        private String date;

        private String media;

        private String conversionDate;

        private BigDecimal conversionRate;

        private BigDecimal amount;

        private String currency;

        private BigDecimal fee;

        private BigDecimal balance;
        
        private String transferDate;
        
    }

}
