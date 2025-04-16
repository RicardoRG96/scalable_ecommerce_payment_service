package com.ricardo.scalable.ecommerce.platform.payment_service.gateway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        Map<String, Object> body = new HashMap<>();
        return null;
    }

    private String generateSignature(PaymentRequest request) {
        Map<String, Object> params = generateBody(request);
        User user = getUser(request.getOrderId());

        
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
        return body;
    }

    private User getUser(Long orderId) {
        Long userId = orderRepository.findById(orderId).orElseThrow().getUser().getId();
        return userRepository.findById(userId).orElseThrow();
    }

}
