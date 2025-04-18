package com.ricardo.scalable.ecommerce.platform.payment_service.gateway;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ricardo.scalable.ecommerce.platform.payment_service.exception.FlowApiException;
import com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto.PaymentRequest;
import com.ricardo.scalable.ecommerce.platform.payment_service.repositories.dto.PaymentResponse;
import static com.ricardo.scalable.ecommerce.platform.payment_service.util.SignatureUtil.*;

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

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        Map<String, Object> params = generateBody(request);
        String signature = createSignedString(params, secretKey);
        params.put("s", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        String body = params.entrySet().stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode((String) e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            flowApiUrl + "/payment/create",
            HttpMethod.POST,
            httpEntity,
            new ParameterizedTypeReference<Map<String, Object>>() {
            }
        );

        Map<String, Object> responseBody = Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new FlowApiException("La respuesta de la API de Flow es nula"));

        String token = Optional.ofNullable((String) responseBody.get("token"))
                .orElseThrow(() -> new FlowApiException("No se encontró el token en la respuesta de FLOW"));

        String url = Optional.ofNullable((String) responseBody.get("url"))
                .orElseThrow(() -> new FlowApiException("No se encontró la URL en la respuesta de FLOW"));

        String redirectPaymentUrl = url + "?token=" + token;

        return new PaymentResponse(token, "PENDING", "FLOW", redirectPaymentUrl);
    }

    private Map<String, Object> generateBody(PaymentRequest request) {
        Map<String, Object> body = new TreeMap<>();
        body.put("apiKey", apiKey);
        body.put("commerceOrder", String.valueOf(request.getOrderId()));
        body.put("subject", "Pago orden " + request.getOrderId());
        body.put("currency", request.getCurrency());
        body.put("amount", request.getAmount());
        body.put("email", request.getEmail());
        body.put("urlConfirmation", urlConfirmation);
        body.put("urlReturn", urlReturn);
        body.put("timeout", 300);
        body.put("payment_currency", request.getCurrency());
        return body;
    }

    // private String generateSignature(PaymentRequest request) throws Exception {
    //     Map<String, Object> params = generateBody(request);

    //     List<String> sortedKeys = new ArrayList<>(params.keySet());
    //     Collections.sort(sortedKeys);

    //     StringBuilder dataToSign = new StringBuilder();
    //     for (String key : sortedKeys) {
    //         dataToSign.append(key).append(params.get(key));
    //     }

    //     Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
    //     SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
    //     sha256_HMAC.init(secretKeySpec);

    //     byte[] hashBytes = sha256_HMAC.doFinal(dataToSign.toString().getBytes("UTF-8"));

    //     StringBuilder hexString = new StringBuilder();
    //     for (byte b : hashBytes) {
    //         hexString.append(String.format("%02x", b));
    //     }

    //     return hexString.toString();
    // }

    // private User getUser(Long orderId) {
    //     Long userId = orderRepository.findById(orderId).orElseThrow().getUser().getId();
    //     return userRepository.findById(userId).orElseThrow();
    // }

}
