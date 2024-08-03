package ru.anseranser.translator_2024.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TargetLanguageNotFoundException extends RuntimeException{
    public TargetLanguageNotFoundException(String targetLanguage) {
        super(String.format("Target language '%s' non found", targetLanguage));
    }
}
