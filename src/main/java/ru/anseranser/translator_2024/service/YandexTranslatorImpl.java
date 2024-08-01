package ru.anseranser.translator_2024.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class YandexTranslatorImpl implements Translator {
    @Override
    public String translate(String sourceLanguage, String targetLanguage, String sourceText) {
        return "";
    }

    @Override
    public String translate(String targetLanguage, String sourceText) {
        return "";
    }
}
