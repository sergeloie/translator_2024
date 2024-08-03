package ru.anseranser.translator_2024.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.anseranser.translator_2024.model.CompletedRequest;
import ru.anseranser.translator_2024.model.TranslationInputDTO;
import ru.anseranser.translator_2024.repository.CompletedRequestRepository;
import ru.anseranser.translator_2024.service.NetworkService;
import ru.anseranser.translator_2024.service.YandexTranslatorService;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/translate")
@RequiredArgsConstructor
public class TranslateController {

    private final YandexTranslatorService yandexTranslatorService;
    private final CompletedRequestRepository completedRequestRepository;
    private final NetworkService networkService;

    @PostMapping
    public String translate(@RequestBody TranslationInputDTO translationInputDto, HttpServletRequest request)
            throws URISyntaxException, JsonProcessingException, ExecutionException, InterruptedException {

        String clientIP = networkService.getClientIp(request);

        String translatedText = yandexTranslatorService.translateWordsMultiThread(
                translationInputDto.getSourceText(),
                translationInputDto.getSourceLanguage(),
                translationInputDto.getTargetLanguage());

        CompletedRequest completedRequest = new CompletedRequest();
        completedRequest.setIpAddress(clientIP);
        completedRequest.setSourceText(translationInputDto.getSourceText());
        completedRequest.setTranslatedText(translatedText);
        completedRequestRepository.save(completedRequest);

        return translatedText;
    }
}

