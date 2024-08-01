package ru.anseranser.translator_2024.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/translate")
public class TranslateController {
    @PostMapping
    public String translate()

}

