package ru.anseranser.translator_2024.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.anseranser.translator_2024.model.YandexRequestDTO;
import ru.anseranser.translator_2024.model.Translation;
import ru.anseranser.translator_2024.model.YandexToken;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class YandexTranslatorService {
    private String TRANSLATE_API_URL = "https://translate.api.cloud.yandex.net/translate/v2/translate";
    private YandexToken yandexToken;
    private final YandexService yandexService;
    private final ObjectMapper objectMapper;

    public String translate(Translation translation) throws JsonProcessingException {
        regenerateToken();
        List<String> words = Arrays.stream(translation.getSourceText().split(" ")).toList();

    }

    public String translateWord(String word, String targetLanguage) throws JsonProcessingException {
        YandexRequestDTO yandexRequestDTO = new YandexRequestDTO();
        yandexRequestDTO.setTexts(List.of(word));
        yandexRequestDTO.setTargetLanguageCode(targetLanguage);
        String json = objectMapper.writeValueAsString(yandexRequestDTO);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(TRANSLATE_API_URL, )
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
