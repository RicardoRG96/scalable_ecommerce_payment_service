package com.ricardo.scalable.ecommerce.platform.payment_service.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
    
}
