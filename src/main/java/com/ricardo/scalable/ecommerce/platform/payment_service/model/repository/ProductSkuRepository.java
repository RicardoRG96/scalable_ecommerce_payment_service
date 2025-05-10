package com.ricardo.scalable.ecommerce.platform.payment_service.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;

public interface ProductSkuRepository extends CrudRepository<ProductSku, Long> {
}
