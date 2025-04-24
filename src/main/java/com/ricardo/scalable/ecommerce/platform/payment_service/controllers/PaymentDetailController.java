package com.ricardo.scalable.ecommerce.platform.payment_service.controllers;

import static com.ricardo.scalable.ecommerce.platform.libs_common.validation.RequestBodyValidation.validation;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.scalable.ecommerce.platform.payment_service.exception.OrderNotFoundException;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentRequest;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.entities.PaymentDetail;
import com.ricardo.scalable.ecommerce.platform.payment_service.services.PaymentDetailService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500") // sacar esta anotacion una vez realizada las pruebas manuales
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
    public ResponseEntity<PaymentDetail> getPaymentDetailByTransactionId(@PathVariable String transactionId) {
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

    @PostMapping("/payment-confirmation")
    public ResponseEntity<String> confirmPayment(@RequestParam String token) {
        paymentDetailService.confirmPayment(token);
        // return ResponseEntity.status(HttpStatus.FOUND)
        //         .location(URI.create("/payment-return"))
        //         .build();
        return ResponseEntity.ok("ok");
    }

    // @GetMapping("/payment-return")
    // public ResponseEntity<Map<String, String>> getPaymentReturn() {
    //     Map<String, String> paymentReturn = Map.of("message", "Payment creation was successful. You are redirected to the order page.");
    //     return ResponseEntity.ok(paymentReturn);
    // }

    @RequestMapping(value = "/payment-return", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Void> returnAfterPayment(@RequestParam(required = false) String token) {
        URI redirectUri = URI.create("http://localhost:8009/return");
        return ResponseEntity.status(302).location(redirectUri).build();
    }

    @GetMapping("/return")
    public ResponseEntity<Map<String, String>> getReturnPage() {
        Map<String, String> paymentReturn = Map.of("message", "Payment creation was successful. You are redirected to the order page.");
        return ResponseEntity.ok(paymentReturn);
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentDetail>> getAllPaymentDetails() {
        List<PaymentDetail> paymentDetails = paymentDetailService.findAll();
        return ResponseEntity.ok(paymentDetails);
    }

    @PostMapping("/")
    public ResponseEntity<?> createAndRedirect(
        @Valid @ModelAttribute PaymentRequest paymentDetail,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        String paymentLink = paymentDetailService.createPaymentAndGetRedirectUrl(paymentDetail)
                .orElseThrow(() -> new OrderNotFoundException("La orden no existe"));

        // if (paymentLink.isPresent()) {
        //     return ResponseEntity.status(HttpStatus.CREATED).body(savedPaymentDetail.orElseThrow());
        // }
        // return ResponseEntity.notFound().build();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(paymentLink))
                .build();
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
