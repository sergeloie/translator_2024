package ru.anseranser.translator_2024;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.anseranser.translator_2024.controller.TranslateController;
import ru.anseranser.translator_2024.exception.SourceLanguageNotFoundException;
import ru.anseranser.translator_2024.model.CompletedRequest;
import ru.anseranser.translator_2024.model.TranslationInputDTO;
import ru.anseranser.translator_2024.repository.CompletedRequestRepository;
import ru.anseranser.translator_2024.service.NetworkService;
import ru.anseranser.translator_2024.service.YandexTranslatorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TranslateTest {


    // Translate text from source language to target language successfully
    @Test
    public void test_translate_success() throws Exception {
        // Arrange
        NetworkService networkService = mock(NetworkService.class);
        YandexTranslatorService yandexTranslatorService = mock(YandexTranslatorService.class);
        CompletedRequestRepository completedRequestRepository = mock(CompletedRequestRepository.class);
        TranslateController translateController = new TranslateController(yandexTranslatorService, completedRequestRepository, networkService);
    
        TranslationInputDTO translationInputDto = new TranslationInputDTO("en", "es", "hello");
        HttpServletRequest request = mock(HttpServletRequest.class);
    
        when(networkService.getClientIp(request)).thenReturn("127.0.0.1");
        when(yandexTranslatorService.translateWordsMultiThread("hello", "en", "es")).thenReturn("hola");
    
        // Act
        String result = translateController.translate(translationInputDto, request);
    
        // Assert
        assertEquals("hola", result);
        verify(completedRequestRepository).save(any(CompletedRequest.class));
    }

    // Handle unsupported source language gracefully
    @Test
    public void test_unsupported_source_language() throws Exception {
        // Arrange
        NetworkService networkService = mock(NetworkService.class);
        YandexTranslatorService yandexTranslatorService = mock(YandexTranslatorService.class);
        CompletedRequestRepository completedRequestRepository = mock(CompletedRequestRepository.class);
        TranslateController translateController = new TranslateController(yandexTranslatorService, completedRequestRepository, networkService);
    
        TranslationInputDTO translationInputDto = new TranslationInputDTO("xx", "es", "hello");
        HttpServletRequest request = mock(HttpServletRequest.class);
    
        when(networkService.getClientIp(request)).thenReturn("127.0.0.1");
        when(yandexTranslatorService.translateWordsMultiThread("hello", "xx", "es")).thenThrow(new SourceLanguageNotFoundException("xx"));
    
        // Act & Assert
        assertThrows(SourceLanguageNotFoundException.class, () -> {
            translateController.translate(translationInputDto, request);
        });
    }
}