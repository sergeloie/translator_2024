package ru.anseranser.translator_2024.service;

public interface Translator {
    String translate(String sourceLanguage, String targetLanguage, String sourceText);
    String translate(String targetLanguage, String sourceText);
}
