package ru.anseranser.translator_2024.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SourceLanguageNotFoundException extends RuntimeException {
    public SourceLanguageNotFoundException(String sourceLanguage) {
        super(String.format("Source language '%s' not found", sourceLanguage));
    }
}
