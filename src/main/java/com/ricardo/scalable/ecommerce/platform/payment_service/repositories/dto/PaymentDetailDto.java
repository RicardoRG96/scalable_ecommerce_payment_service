package com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto;

public class PaymentDetailDto {

    private Long orderId;

    private Integer amount;

    private String currency;

    private String provider;

    private String paymentMethod;

    private Long transactionId;

    private String status;

    public PaymentDetailDto() {
    }

    public PaymentDetailDto(Long orderId, Integer amount, String currency, String provider, String paymentMethod,
            Long transactionId, String status) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.provider = provider;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaymentDetailDto [orderId=" + orderId + ", amount=" + amount + ", currency=" + currency + ", provider="
                + provider + ", paymentMethod=" + paymentMethod + ", transactionId=" + transactionId + ", status="
                + status + "]";
    }

}
