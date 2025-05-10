package com.ricardo.scalable.ecommerce.platform.payment_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.OrderTestData.*;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.ProductSkuTestData.*;
import static com.ricardo.scalable.ecommerce.platform.payment_service.services.testData.utils.OrderItemTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.OrderItem;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.InsufficientStockException;
import com.ricardo.scalable.ecommerce.platform.libs_common.exceptions.ResourceNotFoundException;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.OrderItemRepository;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.PaymentDetailRepository;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.ProductSkuRepository;

@SpringBootTest
public class StockServiceImplTest {

    @MockitoBean
    private OrderItemRepository orderItemRepository;

    @MockitoBean
    private PaymentDetailRepository paymentDetailRepository;

    @MockitoBean
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private StockService stockService;

    @Test
    void verifyStock_whenOrderItemsAreEmpty_shouldThrowResourceNotFoundException() {
        Long orderId = 100L;

        when(orderItemRepository.findByOrderId(orderId)).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> stockService.verifyStock(orderId));
    }

    @Test
    void verifyStock_whenQuantityIsGreaterThanStock_shouldThrowInsufficientStockException() {
        List<OrderItem> orderItems = createListOfOrderItemByOrderId1().orElseThrow();
        orderItems.get(0).setQuantity(21);

        when(orderItemRepository.findByOrderId(1L)).thenReturn(orderItems);
        when(productSkuRepository.findById(1L)).thenReturn(createProductSku001());
        when(productSkuRepository.findById(2L)).thenReturn(createProductSku002());

        assertThrows(InsufficientStockException.class, () -> stockService.verifyStock(1L));
    }

    @Test
    void verifyStock_whenProductSkuNotFound_shouldThrowResourceNotFoundException() {
        List<OrderItem> orderItems = createListOfOrderItemByOrderId1().orElseThrow();

        when(orderItemRepository.findByOrderId(1L)).thenReturn(orderItems);
        when(productSkuRepository.findById(1L)).thenReturn(createProductSku001());
        when(productSkuRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> stockService.verifyStock(1L));
    }

}
