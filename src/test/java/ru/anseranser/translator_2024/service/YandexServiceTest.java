package ru.anseranser.translator_2024.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YandexServiceTest {

    @Autowired
    private YandexService yandexService;

    @Test
    void getIAMFromOAuthTest() throws JsonProcessingException {
        String result = yandexService.getIAMFromOAuth();
        System.out.println(result);
    }
}