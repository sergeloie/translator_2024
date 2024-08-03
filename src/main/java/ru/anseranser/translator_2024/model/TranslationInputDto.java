package ru.anseranser.translator_2024.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranslationInputDto {
    private final String sourceLanguage;
    private final String targetLanguage;
    private final String sourceText;
}
