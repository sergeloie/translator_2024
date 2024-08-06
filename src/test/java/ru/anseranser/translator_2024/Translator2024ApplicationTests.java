package ru.anseranser.translator_2024;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import ru.anseranser.translator_2024.model.YandexToken;
import ru.anseranser.translator_2024.service.YandexTranslatorService;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Translator2024ApplicationTests {
	@Autowired
	YandexTranslatorService yandexTranslatorService;

	@BeforeEach
	void prepareToken() {
		YandexToken yandexToken = new YandexToken();
		yandexToken.setIamToken("t1.hidden");
		yandexToken.setExpiresAt(Instant.parse("2024-08-04T03:04:28.208949107Z"));
		yandexTranslatorService.setYandexToken(yandexToken);
	}

	@Test
	void translateSingleWordTest() throws URISyntaxException, JsonProcessingException {
		String word = "bug";
		var result = yandexTranslatorService.translateWord(word, "ru");
		assertThat(result).isEqualTo("жук");
	}

	@Test
	void translateWordsMultiThreads() throws ExecutionException, InterruptedException, JsonProcessingException {
		String words = "One Two Three Four Five Six Seven Eight Nine Ten Eleven Twelve Thirteen Fourteen Fifteen Sixteen Seventeen Eighteen Nineteen Twenty";
		String assertedWords = "Один Два Три Четыре Пять Шесть Семь Восемь Девять Десять Одиннадцать Двенадцать Тринадцать Четырнадцать Пятнадцать Шестнадцать Семнадцать Восемнадцать Девятнадцать Двадцать";
		String result = yandexTranslatorService.translateWordsMultiThread(words, "en", "ru");
		assertThat(assertedWords).isEqualTo(result);
	}

	@Test
	void translateRussian() throws URISyntaxException, JsonProcessingException {
		String word = "борода";
		String assertedWords = "beard";
		String result = yandexTranslatorService.translateWord(word, "en");
		assertThat(assertedWords).isEqualTo(result);
	}

	@Test
	void translateCzech() throws URISyntaxException, JsonProcessingException {
		String words = "Bezplatná služba Google umožňuje okamžitý překlad slov";
		String assertedWords = "Google's free service lets you translate words instantly";
		String result = yandexTranslatorService.translateWord(words, "en");
		assertThat(assertedWords).isEqualTo(result);
	}
}
