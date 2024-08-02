package ru.anseranser.translator_2024.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class YandexResponseDTO {
    private List<TranslatePair> translations;
    static class TranslatePair {
        private String text;
        private String detectedLanguageCode;
    }
}

