package com.ricardo.scalable.ecommerce.platform.payment_service.services;

public interface StockService {

    void verifyStock(Long orderId);

    void updateStockAfterPayment(String transactionId);

}
