package com.ricardo.scalable.ecommerce.platform.payment_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.OrderItem;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.InsufficientStockException;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.ResourceNotFoundException;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.ProductSkuRepository;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Override
    public void verifyStock(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            Long productSkuId = orderItem.getProductSku().getId();
            int quantity = orderItem.getQuantity();

            productSkuRepository.findById(productSkuId)
                .ifPresentOrElse(productSku -> {
                    if (productSku.getStock() < quantity) {
                        throw new InsufficientStockException("Not enough stock for product SKU: " + productSkuId);
                    }
                }, () -> {
                    throw new ResourceNotFoundException("Product SKU not found: " + productSkuId);
                });
        }
    }

    @Override
    public void updateStockAfterPayment(List<OrderItem> orderItems) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStockAfterPayment'");
    }

}
