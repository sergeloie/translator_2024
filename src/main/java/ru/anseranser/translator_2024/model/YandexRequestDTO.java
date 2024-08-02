package ru.anseranser.translator_2024.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class YandexRequestDTO {

    @Value("${FOLDER_ID_ENV}")
    private String folderId;
    private List<String> texts;
    private String targetLanguageCode;
}
