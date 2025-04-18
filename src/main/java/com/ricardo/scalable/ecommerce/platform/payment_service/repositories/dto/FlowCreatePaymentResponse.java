package com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto;

public class FlowCreatePaymentResponse {

    private String url;

    private String token;

    private Integer flowOrder;

    public FlowCreatePaymentResponse() {
    }

    public FlowCreatePaymentResponse(String url, String token, Integer flowOrder) {
        this.url = url;
        this.token = token;
        this.flowOrder = flowOrder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getFlowOrder() {
        return flowOrder;
    }

    public void setFlowOrder(Integer flowOrder) {
        this.flowOrder = flowOrder;
    }

}
