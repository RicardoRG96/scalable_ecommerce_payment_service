package com.ricardo.scalable.ecommerce.platform.payment_service.controllers;

import static com.ricardo.scalable.ecommerce.platform.libs_common.validation.RequestBodyValidation.validation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.ricardo.scalable.ecommerce.platform.payment_service.exception.PaymentDetailNotFoundException;
import com.ricardo.scalable.ecommerce.platform.payment_service.exception.UnsupportedFlowStatusCodeException;
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
        PaymentDetail paymentDetail = paymentDetailService.findById(id)
                .orElseThrow(() -> new PaymentDetailNotFoundException("El detalle del pago no existe"));
        
        return ResponseEntity.ok(paymentDetail);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentDetail> getPaymentDetailByOrderId(@PathVariable Long orderId) {
        PaymentDetail paymentDetail = paymentDetailService.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentDetailNotFoundException("No existen detalles de pago para esta orden"));
        
        return ResponseEntity.ok(paymentDetail);
    }

    @GetMapping("/currency/{currency}")
    public ResponseEntity<List<PaymentDetail>> getPaymentDetailByCurrency(@PathVariable String currency) {
        List<PaymentDetail> paymentDetail = paymentDetailService.findByCurrency(currency)
                .orElseThrow(() -> new PaymentDetailNotFoundException("No existen detalles de pago para esta moneda"));

        boolean isEmpty = paymentDetail.isEmpty();

        if (isEmpty) {
            throw new PaymentDetailNotFoundException("No existen detalles de pago para esta moneda");
        }
        return ResponseEntity.ok(paymentDetail);
    }

    @GetMapping("/provider/{provider}")
    public ResponseEntity<List<PaymentDetail>> getPaymentDetailByProvider(@PathVariable String provider) {
        List<PaymentDetail> paymentDetail = paymentDetailService.findByProvider(provider)
                .orElseThrow(() -> new PaymentDetailNotFoundException("No se encontraron registros con este proveedor de pago"));

        boolean isEmpty = paymentDetail.isEmpty();

        if (isEmpty) {
            throw new PaymentDetailNotFoundException("No se encontraron registros con este proveedor de pago");
        }
        return ResponseEntity.ok(paymentDetail);
    }

    @GetMapping("/paymentMethod/{paymentMethod}")
    public ResponseEntity<List<PaymentDetail>> getPaymentDetailByPaymentMethod(@PathVariable String paymentMethod) {
        List<PaymentDetail> paymentDetail = paymentDetailService.findByPaymentMethod(paymentMethod)
                .orElseThrow(() -> new PaymentDetailNotFoundException("No se encontraron registros con este metodo de pago"));

        boolean isEmpty = paymentDetail.isEmpty();

        if (isEmpty) {
            throw new PaymentDetailNotFoundException("No se encontraron registros con este metodo de pago");
        }
        return ResponseEntity.ok(paymentDetail);
    }

    @GetMapping("/transactionId/{transactionId}")
    public ResponseEntity<PaymentDetail> getPaymentDetailByTransactionId(@PathVariable String transactionId) {
        PaymentDetail paymentDetail = paymentDetailService.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentDetailNotFoundException("No se encontraron registros con este ID de transacción"));

        return ResponseEntity.ok(paymentDetail);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDetail>> getPaymentDetailByStatus(@PathVariable String status) {
        List<PaymentDetail> paymentDetail = new ArrayList<>();
        try {
            paymentDetail = paymentDetailService.findByStatus(status)
                .orElseThrow(() -> new PaymentDetailNotFoundException("No se encontraron registros con este estado de pago"));

            boolean isEmpty = paymentDetail.isEmpty();

            if (isEmpty) {
                throw new PaymentDetailNotFoundException("No se encontraron registros con este estado de pago");
            }
        } catch (UnsupportedFlowStatusCodeException e) {
            throw new PaymentDetailNotFoundException(e.getMessage());
        }
        return ResponseEntity.ok(paymentDetail);
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

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(paymentLink))
                .build();
    }

    @PostMapping("/payment-confirmation")
    public ResponseEntity<Void> confirmPayment(@RequestParam String token) {
        try {
            paymentDetailService.confirmPayment(token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @RequestMapping(value = "/payment-return", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Void> returnAfterPayment(@RequestParam(required = false) String token) {
        URI redirectUri = URI.create("http://localhost:8009/return");
        return ResponseEntity.status(302).location(redirectUri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaymentDetail(@PathVariable Long id) {
        paymentDetailService.findById(id)
                .orElseThrow(() -> new PaymentDetailNotFoundException("El detalle del pago no existe"));

        paymentDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
