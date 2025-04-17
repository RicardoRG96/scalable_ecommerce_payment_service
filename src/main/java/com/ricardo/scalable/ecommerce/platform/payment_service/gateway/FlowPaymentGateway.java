package com.ricardo.scalable.ecommerce.platform.payment_service.gateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Order;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.User;
import com.ricardo.scalable.ecommerce.platform.payment_service.repositories.OrderRepository;
import com.ricardo.scalable.ecommerce.platform.payment_service.repositories.UserRepository;
import com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto.PaymentRequest;
import com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto.PaymentResponse;

@Service
public class FlowPaymentGateway implements PaymentGateway {

    @Value("${flow.api.key}")
    private String apiKey;

    @Value("${flow.secret.key}")
    private String secretKey;

    @Value("${flow.api.url}")
    private String flowApiUrl;

    @Value("${flow.api.url.confirmation}")
    private String urlConfirmation;

    @Value("${flow.api.url.return}")
    private String urlReturn;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        PaymentResponse paymentResponse = null;
        try {
            Map<String, Object> body = generateBody(request);
            String signature = generateSignature(request);
            body.put("s", signature);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-www-form-urlencoded");

            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                flowApiUrl + "/payment/create",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
            );

            Map<String, Object> responseBody = response.getBody();

            paymentResponse = new PaymentResponse();
            paymentResponse.setTransactionId((String) responseBody.get("token"));
            paymentResponse.setStatus("PENDING");
            paymentResponse.setProvider("FLOW");
            paymentResponse.setMessage("Transacción creada con FLOW");
            
        } catch (Exception e) {
            throw new RuntimeException("Error generating payment", e);
        }
        return paymentResponse;
    }

    private Map<String, Object> generateBody(PaymentRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("apiKey", apiKey);
        body.put("commerceOrder", String.valueOf(request.getOrderId()));
        body.put("subject", "pago para producto en e-commerce");
        body.put("currency", request.getCurrency());
        body.put("amount", request.getAmount());
        body.put("email", getUser(request.getOrderId()).getEmail());
        body.put("urlConfirmation", urlConfirmation);
        body.put("urlReturn", urlReturn);
        body.put("timeout", 300);
        body.put("payment_currency", request.getCurrency());
        return body;
    }

    private String generateSignature(PaymentRequest request) throws Exception {
        Map<String, Object> params = generateBody(request);

        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);

        StringBuilder dataToSign = new StringBuilder();
        for (String key : sortedKeys) {
            dataToSign.append(key).append(params.get(key));
        }

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secretKeySpec);

        byte[] hashBytes = sha256_HMAC.doFinal(dataToSign.toString().getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    private User getUser(Long orderId) {
        Long userId = orderRepository.findById(orderId).orElseThrow().getUser().getId();
        return userRepository.findById(userId).orElseThrow();
    }

}
