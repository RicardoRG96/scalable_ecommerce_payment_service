package com.ricardo.scalable.ecommerce.platform.payment_service.controllers;

import static com.ricardo.scalable.ecommerce.platform.libs_common.validation.RequestBodyValidation.validation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.scalable.ecommerce.platform.payment_service.entities.PaymentDetail;
import com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto.PaymentDetailDto;
import com.ricardo.scalable.ecommerce.platform.payment_service.services.PaymentDetailService;

import jakarta.validation.Valid;

@RestController
public class PaymentDetailController {

    @Autowired
    private PaymentDetailService paymentDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetail> getPaymentDetailById(@PathVariable Long id) {
        Optional<PaymentDetail> paymentDetail = paymentDetailService.findById(id);
        if (paymentDetail.isPresent()) {
            return ResponseEntity.ok(paymentDetail.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentDetail> getPaymentDetailByOrderId(@PathVariable Long orderId) {
        Optional<PaymentDetail> paymentDetail = paymentDetailService.findByOrderId(orderId);
        if (paymentDetail.isPresent()) {
            return ResponseEntity.ok(paymentDetail.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/currency/{currency}")
    public ResponseEntity<List<PaymentDetail>> getPaymentDetailByCurrency(@PathVariable String currency) {
        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByCurrency(currency);
        boolean isEmpty = paymentDetail.orElseThrow().isEmpty();

        if (paymentDetail.isPresent() && !isEmpty) {
            return ResponseEntity.ok(paymentDetail.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/provider/{provider}")
    public ResponseEntity<List<PaymentDetail>> getPaymentDetailByProvider(@PathVariable String provider) {
        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByProvider(provider);
        boolean isEmpty = paymentDetail.orElseThrow().isEmpty();

        if (paymentDetail.isPresent() && !isEmpty) {
            return ResponseEntity.ok(paymentDetail.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/paymentMethod/{paymentMethod}")
    public ResponseEntity<List<PaymentDetail>> getPaymentDetailByPaymentMethod(@PathVariable String paymentMethod) {
        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByPaymentMethod(paymentMethod);
        boolean isEmpty = paymentDetail.orElseThrow().isEmpty();

        if (paymentDetail.isPresent() && !isEmpty) {
            return ResponseEntity.ok(paymentDetail.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/transactionId/{transactionId}")
    public ResponseEntity<PaymentDetail> getPaymentDetailByTransactionId(@PathVariable Long transactionId) {
        Optional<PaymentDetail> paymentDetail = paymentDetailService.findByTransactionId(transactionId);
        if (paymentDetail.isPresent()) {
            return ResponseEntity.ok(paymentDetail.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDetail>> getPaymentDetailByStatus(@PathVariable String status) {
        Optional<List<PaymentDetail>> paymentDetail = paymentDetailService.findByStatus(status);
        boolean isEmpty = paymentDetail.orElseThrow().isEmpty();

        if (paymentDetail.isPresent() && !isEmpty) {
            return ResponseEntity.ok(paymentDetail.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentDetail>> getAllPaymentDetails() {
        List<PaymentDetail> paymentDetails = paymentDetailService.findAll();
        return ResponseEntity.ok(paymentDetails);
    }

    @PostMapping("/")
    public ResponseEntity<?> createPaymentDetail(
        @Valid @RequestBody PaymentDetailDto paymentDetail,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<PaymentDetail> savedPaymentDetail = paymentDetailService.save(paymentDetail);
        if (savedPaymentDetail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPaymentDetail.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentDetail(
        @Valid @RequestBody PaymentDetailDto paymentDetail,
        @PathVariable Long id,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<PaymentDetail> updatedPaymentDetail = paymentDetailService.update(id, paymentDetail);
        if (updatedPaymentDetail.isPresent()) {
            return ResponseEntity.ok(updatedPaymentDetail.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentDetail(@PathVariable Long id) {
        Optional<PaymentDetail> paymentDetail = paymentDetailService.findById(id);
        if (paymentDetail.isPresent()) {
            paymentDetailService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
}
