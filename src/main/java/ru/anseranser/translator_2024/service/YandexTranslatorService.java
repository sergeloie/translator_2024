package ru.anseranser.translator_2024.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.anseranser.translator_2024.model.YandexRequestDTO;
import ru.anseranser.translator_2024.model.Translation;
import ru.anseranser.translator_2024.model.YandexResponseDTO;
import ru.anseranser.translator_2024.model.YandexToken;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Setter
public class YandexTranslatorService {
    private String TRANSLATE_API_URL = "https://translate.api.cloud.yandex.net/translate/v2/translate";
    private YandexToken yandexToken;
    private final YandexService yandexService;
    private final ObjectMapper objectMapper;

    public String translate(Translation translation) throws JsonProcessingException {
        regenerateToken();
        List<String> words = Arrays.stream(translation.getSourceText().split(" ")).toList();

        return "";

    }

    public String translateWord(String word, String targetLanguage) throws JsonProcessingException, URISyntaxException {
        YandexRequestDTO yandexRequestDTO = new YandexRequestDTO();
        yandexRequestDTO.setTexts(List.of(word));
        yandexRequestDTO.setTargetLanguageCode(targetLanguage);
        String json = objectMapper.writeValueAsString(yandexRequestDTO);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(yandexToken.getIamToken());
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<YandexResponseDTO> response = restTemplate.postForEntity(new URI(TRANSLATE_API_URL), entity, YandexResponseDTO.class);
        return response.getBody().getTranslations().toString();
    }

    private void regenerateToken() throws JsonProcessingException {
        if (this.yandexToken == null) {
            this.yandexToken = yandexService.getIAMTokenFromOAuth();
        } else {
            Duration duration = Duration.between(Instant.now(), this.yandexToken.getExpiresAt());
            if (duration.toHours() < 6) {
                this.yandexToken = yandexService.getIAMTokenFromOAuth();
            }
        }
    }
}
