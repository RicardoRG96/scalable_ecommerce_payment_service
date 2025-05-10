package com.ricardo.scalable.ecommerce.platform.payment_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.OrderItem;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.InsufficientStockException;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.ResourceNotFoundException;
import com.ricardo.scalable.ecommerce.platform.payment_service.exception.PaymentNotConfirmedException;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.PaymentStatus;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.entities.PaymentDetail;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.OrderItemRepository;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.PaymentDetailRepository;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.ProductSkuRepository;

import jakarta.transaction.Transactional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void verifyStock(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        if (orderItems.isEmpty()) {
            throw new ResourceNotFoundException("No order items found for order ID: " + orderId);
        }

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
    @Transactional
    public void updateStockAfterPayment(String transactionId) {
        validateCompletedPayment(transactionId);

        List<OrderItem> orderItems = getOrderItemsByTransactionId(transactionId);
        
        if (orderItems.isEmpty()) {
            throw new ResourceNotFoundException("No order items found for transaction ID: " + transactionId);
        }

        for (OrderItem orderItem : orderItems) {
            Long productSkuId = orderItem.getProductSku().getId();
            int quantity = orderItem.getQuantity();

            productSkuRepository.findById(productSkuId)
                .ifPresentOrElse(productSku -> {
                    Integer newStock = productSku.getStock() - quantity;
                    if (newStock < 0) {
                        throw new InsufficientStockException("Not enough stock for product SKU: " + productSkuId);
                    }
                    productSku.setStock(newStock);
                    productSkuRepository.save(productSku);
                }, () -> {
                    throw new ResourceNotFoundException("Product SKU not found: " + productSkuId);
                });
        }
    }

    private void validateCompletedPayment(String transactionId) {
        PaymentDetail paymentDetail = paymentDetailRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Payment not found for transaction ID: " + transactionId));

        PaymentStatus paymentStatus = paymentDetail.getStatus();
        Long orderId = paymentDetail.getOrder().getId();

        if (paymentStatus != PaymentStatus.COMPLETED) {
            throw new PaymentNotConfirmedException(
                "Stock cannot be updated because order " + orderId + "has not been confirmed for payment.");
        }
    }

    private List<OrderItem> getOrderItemsByTransactionId(String transactionId) {
        Long orderId = paymentDetailRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Payment not found for transaction ID: " + transactionId)).getOrder().getId();

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        return orderItems;
    }

}
