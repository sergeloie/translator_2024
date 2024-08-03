package ru.anseranser.translator_2024.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.anseranser.translator_2024.model.YandexToken;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YandexServiceTest {

    @Autowired
    private YandexService yandexService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    YandexTranslatorService yandexTranslatorService;


    @Test
    void DummyTest() throws JsonProcessingException {
        String json = """
{
 "iamToken": "t1.9euelZrJmsqMjZaOlp2Kj57NkJGNkO3rnpWalJSWnY6NkcaWz5zOz5DMjo7l8_daVE1K-e8Hbyhr_t3z9xoDS0r57wdvKGv-zef1656Vmp2QlZvLkMrLzZaQzozKz4qO7_zF656Vmp2QlZvLkMrLzZaQzozKz4qO.-nA_obECxeuqpYxR7H3vNly8nDVqDdn6xYEwuvO-IIVZGSbgB_pQc8tu7q1dowd_XQ5R4QqyxU1rcgvZ08yJAA",
 "expiresAt": "2024-08-02T20:15:33.311806072Z"
}
""";
        YandexToken token = objectMapper.readValue(json, YandexToken.class);
        System.out.println(Instant.now());
        System.out.println(token);

    }

    @Test
    void TransTest() throws URISyntaxException, JsonProcessingException {
        YandexToken token = new YandexToken();
        token.setIamToken("t1.9euelZrJmsqMjZaOlp2Kj57NkJGNkO3rnpWalJSWnY6NkcaWz5zOz5DMjo7l8_daVE1K-e8Hbyhr_t3z9xoDS0r57wdvKGv-zef1656Vmp2QlZvLkMrLzZaQzozKz4qO7_zF656Vmp2QlZvLkMrLzZaQzozKz4qO.-nA_obECxeuqpYxR7H3vNly8nDVqDdn6xYEwuvO-IIVZGSbgB_pQc8tu7q1dowd_XQ5R4QqyxU1rcgvZ08yJAA");
        token.setExpiresAt(Instant.parse("2024-08-02T20:15:33.311806072Z"));
        yandexTranslatorService.setYandexToken(token);
        String word = "яблоко";
        String result = yandexTranslatorService.translateWord(word, "en");
        System.out.println(result);
    }

    @Test
    void tokenTest() throws JsonProcessingException {
        String result = yandexService.getIAMTokenFromOAuth().getIamToken();
        System.out.println(result);
    }

}