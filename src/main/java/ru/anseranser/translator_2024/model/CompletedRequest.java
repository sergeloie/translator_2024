package ru.anseranser.translator_2024.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletedRequest {
    private long id;
    private String ipAddress;
    private String sourceText;
    private String translatedText;
}