package com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto;

public class PaymentRequest {

    private Long orderId;

    private Integer amount;

    private String currency;

    private String email;

    public PaymentRequest() {
    }

    public PaymentRequest(Long orderId, Integer amount, String currency, String email) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PaymentRequest [orderId=" + orderId + ", amount=" + amount + ", currency=" + currency + ", email="
                + email + "]";
    }
    
}
