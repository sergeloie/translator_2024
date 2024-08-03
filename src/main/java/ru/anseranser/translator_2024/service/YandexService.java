package ru.anseranser.translator_2024.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.anseranser.translator_2024.model.YandexToken;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class YandexService {

    @Value("${YANDEX_TOKEN_URL}")
    private String YANDEX_TOKEN_URL;

    @Value("${YANDEX_OAUTH_TOKEN_ENV}")
    private String YANDEX_OAUTH_TOKEN;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public YandexToken getIAMTokenFromOAuth() throws JsonProcessingException {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("yandexPassportOauthToken", YANDEX_OAUTH_TOKEN);
        String jsonRequestBody = objectMapper.writeValueAsString(requestBody);

        HttpEntity<String> request = new HttpEntity<>(jsonRequestBody);
        ResponseEntity<String> response = restTemplate.postForEntity(YANDEX_TOKEN_URL, request, String.class);

        return objectMapper.readValue(response.getBody(), YandexToken.class);
    }
}
