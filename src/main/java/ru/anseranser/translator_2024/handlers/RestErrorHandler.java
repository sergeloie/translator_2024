package ru.anseranser.translator_2024.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.anseranser.translator_2024.exception.SourceLanguageNotFoundException;
import ru.anseranser.translator_2024.exception.TargetLanguageNotFoundException;
import ru.anseranser.translator_2024.exception.TranslationResourceNotAccessibleException;

import java.net.URISyntaxException;

@ControllerAdvice
public class RestErrorHandler {
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleJsonProcessingException(JsonProcessingException exception) {
        String error = exception.getMessage();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SourceLanguageNotFoundException.class)
    public ResponseEntity<String> handleSourceLanguageNotFoundException(SourceLanguageNotFoundException exception) {
        String error = exception.getMessage();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TargetLanguageNotFoundException.class)
    public ResponseEntity<String> handleTargetLanguageNotFoundException(TargetLanguageNotFoundException exception) {
        String error = exception.getMessage();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TranslationResourceNotAccessibleException.class)
    public ResponseEntity<String> handleTranslationResourceNotAccessibleException(TranslationResourceNotAccessibleException exception) {
        String error = exception.getMessage();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<String> handleURISyntaxException(URISyntaxException exception) {
        String error = "Incorrect URL format: " + exception.getReason();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
