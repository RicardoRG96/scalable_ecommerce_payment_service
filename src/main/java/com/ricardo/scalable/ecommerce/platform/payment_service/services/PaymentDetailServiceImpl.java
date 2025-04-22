package com.ricardo.scalable.ecommerce.platform.payment_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Order;
import com.ricardo.scalable.ecommerce.platform.libs_common.enums.PaymentStatus;
import com.ricardo.scalable.ecommerce.platform.payment_service.exception.FlowApiException;
import com.ricardo.scalable.ecommerce.platform.payment_service.gateway.PaymentGateway;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentRequest;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentResponse;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.entities.PaymentDetail;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.OrderRepository;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.PaymentDetailRepository;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService {

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Optional<PaymentDetail> findById(Long id) {
        return paymentDetailRepository.findById(id);
    }

    @Override
    public Optional<PaymentDetail> findByOrderId(Long orderId) {
        return paymentDetailRepository.findByOrderId(orderId);
    }

    @Override
    public Optional<List<PaymentDetail>> findByCurrency(String currency) {
        return paymentDetailRepository.findByCurrency(currency);
    }

    @Override
    public Optional<List<PaymentDetail>> findByProvider(String provider) {
        return paymentDetailRepository.findByProvider(provider);
    }

    @Override
    public Optional<List<PaymentDetail>> findByPaymentMethod(String paymentMethod) {
        return paymentDetailRepository.findByPaymentMethod(paymentMethod);
    }

    @Override
    public Optional<PaymentDetail> findByTransactionId(String transactionId) {
        return paymentDetailRepository.findByTransactionId(transactionId);
    }

    @Override
    public Optional<List<PaymentDetail>> findByStatus(String status) {
        return paymentDetailRepository.findByStatus(status);
    }

    @Override
    public List<PaymentDetail> findAll() {
        return (List<PaymentDetail>) paymentDetailRepository.findAll();
    }

    @Override
    public Optional<String> createPaymentAndGetRedirectUrl(PaymentRequest paymentDetail) {
        Optional<Order> orderOptional = orderRepository.findById(paymentDetail.getOrderId());
        if (orderOptional.isPresent()) {
            PaymentDetail paymentDetailToCreate = new PaymentDetail();
            paymentDetailToCreate.setOrder(orderOptional.orElseThrow());
            paymentDetailToCreate.setAmount(paymentDetail.getAmount());
            paymentDetailToCreate.setCurrency(paymentDetail.getCurrency());
            paymentDetailToCreate.setStatus(PaymentStatus.valueOf("PENDING"));

            PaymentResponse paymentResponse = paymentGateway.processPayment(paymentDetail);
            paymentDetailToCreate.setProvider(paymentResponse.getProvider());
            paymentDetailToCreate.setTransactionId(paymentResponse.getTransactionId());

            paymentDetailRepository.save(paymentDetailToCreate);

            return Optional.of(paymentResponse.getPaymentLink());
        }
        return Optional.empty();
    }

    @Override
    public void confirmPayment(String token) {
        String status = paymentGateway.getPaymentStatus(token);

        PaymentDetail payment = paymentDetailRepository.findByTransactionId(token)
                .orElseThrow(() -> new FlowApiException("Transacción no encontrada"));

        payment.setStatus(PaymentStatus.valueOf(status));

        paymentDetailRepository.save(payment);
    }

    @Override
    public void delete(Long id) {
        paymentDetailRepository.deleteById(id);
    }

}
