package com.ricardo.scalable.ecommerce.platform.payment_service.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentRequest {

    @NotNull
    @Min(1)
    private Long orderId;

    @NotNull
    @Min(1)
    private Integer amount;

    @NotBlank
    private String currency;

    @NotBlank
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
