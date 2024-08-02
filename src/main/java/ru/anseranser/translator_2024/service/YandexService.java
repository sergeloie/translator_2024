package ru.anseranser.translator_2024.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class YandexService {
    private String TOKEN_URL = "https://iam.api.cloud.yandex.net/iam/v1/tokens";

    @Value("${YANDEX_OAUTH_TOKEN_ENV}")
    private String YANDEX_OAUTH_TOKEN;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public String getIAMFromOAuth() throws JsonProcessingException {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("yandexPassportOauthToken", YANDEX_OAUTH_TOKEN);
        String jsonRequestBody = objectMapper.writeValueAsString(requestBody);

        HttpEntity<String> request = new HttpEntity<>(jsonRequestBody);
        ResponseEntity<String> response = restTemplate.postForEntity(TOKEN_URL, request, String.class);
        return response.getBody();
    }
}
