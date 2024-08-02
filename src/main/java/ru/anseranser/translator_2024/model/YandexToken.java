package ru.anseranser.translator_2024.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class YandexToken {
    private String iamToken;
    private Instant expiresAt;
}
