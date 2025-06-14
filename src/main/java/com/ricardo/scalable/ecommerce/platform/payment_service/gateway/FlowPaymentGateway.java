package com.ricardo.scalable.ecommerce.platform.payment_service.gateway;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ricardo.scalable.ecommerce.platform.payment_service.exception.FlowApiException;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.PaymentStatus;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.FlowCreatePaymentResponse;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.FlowPaymentStatusResponse;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentRequest;
import com.ricardo.scalable.ecommerce.platform.payment_service.model.dto.PaymentResponse;

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

        ResponseEntity<FlowCreatePaymentResponse> response = restTemplate.exchange(
            flowApiUrl + "/payment/create",
            HttpMethod.POST,
            httpEntity,
            FlowCreatePaymentResponse.class
        );

        FlowCreatePaymentResponse responseBody = Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new FlowApiException("La respuesta de la API de Flow es nula"));

        String token = Optional.ofNullable((String) responseBody.getToken())
                .orElseThrow(() -> new FlowApiException("No se encontró el token en la respuesta de FLOW"));

        String url = Optional.ofNullable((String) responseBody.getUrl())
                .orElseThrow(() -> new FlowApiException("No se encontró la URL en la respuesta de FLOW"));

        String redirectPaymentUrl = url + "?token=" + token;

        return new PaymentResponse(token, "PENDING", "FLOW", "Pago generado", redirectPaymentUrl);
    }

    private Map<String, Object> generateBody(PaymentRequest request) {
        Map<String, Object> body = new TreeMap<>();
        body.put("apiKey", apiKey);
        body.put("commerceOrder", String.valueOf(request.getOrderId()));
        body.put("subject", "Pago orden " + request.getOrderId());
        body.put("currency", request.getCurrency());
        body.put("amount", request.getAmount().toString());
        body.put("email", request.getEmail());
        body.put("urlConfirmation", urlConfirmation);
        body.put("urlReturn", urlReturn);
        body.put("timeout", "300");
        body.put("payment_currency", request.getCurrency());
        return body;
    }

    @Override
    public String getPaymentStatus(String token) {
        FlowPaymentStatusResponse response = getFlowPaymentStatusResponse(token)
                .orElseThrow(() -> new FlowApiException("La respuesta de la API de Flow es nula"));

        int flowStatusCode = response.getStatus();
        PaymentStatus paymentStatus = PaymentStatus.fromCode(flowStatusCode);

        return paymentStatus.getDescription();
    }

    private Optional<FlowPaymentStatusResponse> getFlowPaymentStatusResponse(String token) {
        Map<String, Object> params = new TreeMap<>();
        params.put("apiKey", apiKey);
        params.put("token", token);

        String signature = createSignedString(params, secretKey);
        params.put("s", signature);

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(flowApiUrl + "/payment/getStatus")
                .queryParam("apiKey", apiKey)
                .queryParam("token", token)
                .queryParam("s", signature);

        ResponseEntity<FlowPaymentStatusResponse> response = restTemplate.getForEntity(
            uri.toUriString(),
            FlowPaymentStatusResponse.class
        );

        return Optional.ofNullable(response.getBody());
    }

    @Override
    public String getPaymentMethod(String token) {
        FlowPaymentStatusResponse response = getFlowPaymentStatusResponse(token)
                .orElseThrow(() -> new FlowApiException("La respuesta de la API de Flow es nula"));

        String flowPaymentMethod = response.getPaymentData().getMedia();

        return flowPaymentMethod;
    }

}
