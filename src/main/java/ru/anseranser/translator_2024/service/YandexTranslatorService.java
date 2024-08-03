package ru.anseranser.translator_2024.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.anseranser.translator_2024.exception.SourceLanguageNotFoundException;
import ru.anseranser.translator_2024.exception.TargetLanguageNotFoundException;
import ru.anseranser.translator_2024.exception.TranslationResourceNotAccessibleException;
import ru.anseranser.translator_2024.model.YandexRequestDTO;
import ru.anseranser.translator_2024.model.YandexResponseDTO;
import ru.anseranser.translator_2024.model.YandexToken;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Service
@Setter
public class YandexTranslatorService {
    @Value("${YANDEX_TRANSLATE_API_URL}")
    private String YANDEX_TRANSLATE_API_URL;
    @Value("${SUPPORTED_LANGUAGES}")
    private List<String> supportedLanguages;
    private YandexToken yandexToken;
    private final YandexService yandexService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

/*    public String translate(String textToBeTranslated, String sourceLanguage, String targetLanguage) throws JsonProcessingException, URISyntaxException {

        if (!supportedLanguages.contains(sourceLanguage)) {
            throw new SourceLanguageNotFoundException(sourceLanguage);
        }

        if (!supportedLanguages.contains(targetLanguage)) {
            throw new TargetLanguageNotFoundException(targetLanguage);
        }

        regenerateToken();
        List<String> words = Arrays.stream(textToBeTranslated.split("\\s+")).toList();
        List<String> translatedWords = new ArrayList<>();
        for (String word : words) {
            translatedWords.add(translateWord(word, targetLanguage));
        }
        return String.join(" ", translatedWords);
    }*/

    public String translateWord(String word, String targetLanguage) throws JsonProcessingException, URISyntaxException {
        YandexRequestDTO yandexRequestDTO = new YandexRequestDTO();
        yandexRequestDTO.setTexts(List.of(word));
        yandexRequestDTO.setTargetLanguageCode(targetLanguage);
        String json = objectMapper.writeValueAsString(yandexRequestDTO);
//        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(yandexToken.getIamToken());
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<YandexResponseDTO> response = restTemplate.postForEntity(new URI(YANDEX_TRANSLATE_API_URL), entity, YandexResponseDTO.class);
        if (response.getStatusCode().isError()) {
            throw new TranslationResourceNotAccessibleException("Translation resource not accessible");
        }
        return Objects.requireNonNull(response.getBody()).getTranslations().getFirst().getText();
    }

    public String translateWordsMultiThread(String textToBeTranslated, String sourceLanguage, String targetLanguage)
            throws ExecutionException, InterruptedException, JsonProcessingException {

        if (!supportedLanguages.contains(sourceLanguage)) {
            throw new SourceLanguageNotFoundException(sourceLanguage);
        }

        if (!supportedLanguages.contains(targetLanguage)) {
            throw new TargetLanguageNotFoundException(targetLanguage);
        }

        regenerateToken();

        List<Future<String>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<String> words = Arrays.stream(textToBeTranslated.split("\\s+")).toList();

        for (String word : words) {
            Callable<String> task = () -> translateWord(word, targetLanguage);
            Future<String> future = executor.submit(task);
            futures.add(future);
        }

        List<String> translatedWords = new ArrayList<>();
        for (Future<String> future : futures) {
                translatedWords.add(future.get());
        }
        executor.shutdown();
        return String.join(" ", translatedWords);
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
