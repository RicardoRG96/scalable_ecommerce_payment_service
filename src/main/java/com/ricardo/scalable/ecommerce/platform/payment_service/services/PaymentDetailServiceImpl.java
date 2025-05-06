package com.ricardo.scalable.ecommerce.platform.payment_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Order;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.PaymentStatus;
import com.ricardo.scalable.ecommerce.platform.payment_service.exception.FlowApiException;
import com.ricardo.scalable.ecommerce.platform.payment_service.exception.OrderNotFoundException;
import com.ricardo.scalable.ecommerce.platform.payment_service.exception.PaymentDetailNotFoundException;
import com.ricardo.scalable.ecommerce.platform.payment_service.gateway.PaymentGateway;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentRequest;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentResponse;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.entities.PaymentDetail;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.OrderRepository;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.repository.PaymentDetailRepository;

import jakarta.transaction.Transactional;

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
        PaymentStatus paymentStatus = PaymentStatus.fromDescription(status);
        return paymentDetailRepository.findByStatus(paymentStatus);
    }

    @Override
    public List<PaymentDetail> findAll() {
        return (List<PaymentDetail>) paymentDetailRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<String> createPaymentAndGetRedirectUrl(PaymentRequest paymentDetailRequest) {
        Optional<Order> orderOptional = orderRepository.findById(paymentDetailRequest.getOrderId());
        if (orderOptional.isPresent()) {
            PaymentDetail paymentDetail = getOrCreatePaymentDetail(paymentDetailRequest.getOrderId())
                    .orElseThrow(() -> new PaymentDetailNotFoundException("No se encontró el detalle del pago"));
            
            paymentDetail.setOrder(
                orderOptional.orElseThrow(() -> new OrderNotFoundException("No se encontró la orden"))
            );
            paymentDetail.setAmount(paymentDetailRequest.getAmount());
            paymentDetail.setCurrency(paymentDetailRequest.getCurrency());
            paymentDetail.setStatus(PaymentStatus.PENDING);

            PaymentResponse paymentResponse = paymentGateway.processPayment(paymentDetailRequest);
            paymentDetail.setProvider(paymentResponse.getProvider());
            paymentDetail.setTransactionId(paymentResponse.getTransactionId());

            paymentDetailRepository.save(paymentDetail);

            return Optional.of(paymentResponse.getPaymentLink());
        }
        return Optional.empty();
    }

    private Optional<PaymentDetail> getOrCreatePaymentDetail(Long orderId) {
        List<PaymentStatus> statusList = List.of(PaymentStatus.PENDING, PaymentStatus.FAILED);
        return paymentDetailRepository
                .findFirstByOrderIdAndStatusIn(orderId, statusList)
                .or(() -> Optional.of(new PaymentDetail()));
    }

    @Override
    @Transactional
    public void confirmPayment(String token) {
        String status = paymentGateway.getPaymentStatus(token);
        String paymentMethod = paymentGateway.getPaymentMethod(token);

        PaymentDetail payment = paymentDetailRepository.findByTransactionId(token)
                .orElseThrow(() -> new FlowApiException("Transacción no encontrada"));

        payment.setStatus(PaymentStatus.valueOf(status));
        payment.setPaymentMethod(paymentMethod);

        paymentDetailRepository.save(payment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        paymentDetailRepository.deleteById(id);
    }

}
