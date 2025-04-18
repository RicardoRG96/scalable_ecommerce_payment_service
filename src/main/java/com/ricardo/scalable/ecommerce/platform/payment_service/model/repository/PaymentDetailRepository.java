package com.ricardo.scalable.ecommerce.platform.payment_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.payment_service.entities.PaymentDetail;

public interface PaymentDetailRepository extends CrudRepository<PaymentDetail, Long> {
    
    Optional<PaymentDetail> findByOrderId(Long orderId);

    Optional<List<PaymentDetail>> findByCurrency(String currency);

    Optional<List<PaymentDetail>> findByProvider(String provider);

    Optional<List<PaymentDetail>> findByPaymentMethod(String paymentMethod);

    Optional<PaymentDetail> findByTransactionId(Long transactionId);

    Optional<List<PaymentDetail>> findByStatus(String status);

}
