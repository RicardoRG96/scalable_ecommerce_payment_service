package com.ricardo.scalable.ecommerce.platform.payment_service.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

}
