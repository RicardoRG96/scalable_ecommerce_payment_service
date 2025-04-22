package com.ricardo.scalable.ecommerce.platform.payment_service.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.payment_service.model.entities.PaymentDetail;

public interface PaymentDetailRepository extends CrudRepository<PaymentDetail, Long> {
    
    Optional<PaymentDetail> findByOrderId(Long orderId);

    Optional<List<PaymentDetail>> findByCurrency(String currency);

    Optional<List<PaymentDetail>> findByProvider(String provider);

    Optional<List<PaymentDetail>> findByPaymentMethod(String paymentMethod);

    Optional<PaymentDetail> findByTransactionId(String transactionId);

    Optional<List<PaymentDetail>> findByStatus(String status);

}
