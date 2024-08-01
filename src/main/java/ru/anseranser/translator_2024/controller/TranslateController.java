package ru.anseranser.translator_2024.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.anseranser.translator_2024.model.CompletedRequest;
import ru.anseranser.translator_2024.model.TranslationInputDto;
import ru.anseranser.translator_2024.repository.CompletedRequestRepository;
import ru.anseranser.translator_2024.service.NetworkService;
import ru.anseranser.translator_2024.service.Translator;

@RestController
@RequestMapping("/translate")
@RequiredArgsConstructor
public class TranslateController {

    private final Translator translator;
    private final CompletedRequestRepository completedRequestRepository;
    private final NetworkService networkService;

    @PostMapping
    public String translate(@RequestBody TranslationInputDto translationInputDto, HttpServletRequest request) {
        String clientIP = networkService.getClientIp(request);
        String translatedText = translator.translate(
                translationInputDto.getSourceLanguage(),
                translationInputDto.getTargetLanguage(),
                translationInputDto.getSourceText());
        CompletedRequest completedRequest = new CompletedRequest();
        completedRequest.setIpAddress(clientIP);
        completedRequest.setSourceText(translationInputDto.getSourceText());
        completedRequest.setTranslatedText(translatedText);
        completedRequestRepository.save(completedRequest);

//        return translatedText;
        return "it works" + clientIP;
    }

}

