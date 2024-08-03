package ru.anseranser.translator_2024.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TranslatePair {
    @JsonProperty("text")
    private String text;
    @JsonProperty("detectedLanguageCode")
    private String detectedLanguageCode;
}
