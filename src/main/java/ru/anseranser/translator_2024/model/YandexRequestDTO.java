package ru.anseranser.translator_2024.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Setter
@Getter
public class YandexRequestDTO {

    private final String folderId = System.getenv("FOLDER_ID");
    private List<String> texts;
    private String targetLanguageCode;
}
