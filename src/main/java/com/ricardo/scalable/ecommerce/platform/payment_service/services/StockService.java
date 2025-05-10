package com.ricardo.scalable.ecommerce.platform.payment_service.services;

import java.util.List;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.OrderItem;

public interface StockService {

    void verifyStock(List<OrderItem> orderItems);

    void updateStockAfterPayment(List<OrderItem> orderItems);

}
