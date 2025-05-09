package com.ricardo.scalable.ecommerce.platform.payment_service.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
