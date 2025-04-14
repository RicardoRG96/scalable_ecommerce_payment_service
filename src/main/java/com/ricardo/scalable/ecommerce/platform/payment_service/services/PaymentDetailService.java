package com.ricardo.scalable.ecommerce.platform.payment_service.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.payment_service.entities.PaymentDetail;
import com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto.PaymentDetailDto;

public interface PaymentDetailService {

    Optional<PaymentDetail> findById(Long id);

    Optional<PaymentDetail> findByOrderId(Long orderId);

    Optional<List<PaymentDetail>> findByCurrency(String currency);

    Optional<List<PaymentDetail>> findByProvider(String provider);

    Optional<List<PaymentDetail>> findByPaymentMethod(String paymentMethod);

    Optional<PaymentDetail> findByTransactionId(Long transactionId);

    Optional<List<PaymentDetail>> findByStatus(String status);

    List<PaymentDetail> findAll();

    Optional<PaymentDetail> save(PaymentDetailDto paymentDetail);

    Optional<PaymentDetail> update(Long id, PaymentDetailDto paymentDetail);

    void delete(Long id);
}
