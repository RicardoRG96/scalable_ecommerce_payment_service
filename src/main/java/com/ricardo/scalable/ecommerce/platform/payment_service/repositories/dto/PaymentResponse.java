package com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto;

public class PaymentResponse {

    private String transactionId;

    private String status;

    private String provider;

    private String message;

    private String paymentLink;

    public PaymentResponse() {
    }

    public PaymentResponse(String transactionId, String status, String provider, String message, String paymentLink) {
        this.transactionId = transactionId;
        this.status = status;
        this.provider = provider;
        this.message = message;
        this.paymentLink = paymentLink;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    @Override
    public String toString() {
        return "PaymentResponse [transactionId=" + transactionId + ", status=" + status + ", provider=" + provider
                + ", message=" + message + ", paymentLink=" + paymentLink + "]";
    }

}
