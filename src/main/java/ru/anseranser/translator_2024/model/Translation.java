package ru.anseranser.translator_2024.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Translation {
    private String sourceLanguage;
    private String targetLanguage;
    private String sourceText;
}
