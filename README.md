# translator_2024

Приложение реализует перевод текста с помощью API Yandex Translate

Так как для доступа к API Yandex Translate требуются ключи и для удобства запуска, приложение упковано в Docker.  
Для запуска приложения необходимо скачать образ  
```shell
docker pull anseranser/translator_2024:v1
```

и запустить его
```shell
docker run -p 9090:9090 --name=translator_2024 anseranser/translator_2024:v1
```

после чего можно отправить запрос на перевод текста с помощью curl
```shell
curl -X POST http://127.0.0.1:9090/translate -H "Content-Type: application/json" -d '{"sourceLanguage":"ru","targetLanguage":"en","sourceText":"молоко"}'
```

или с помощью Postman отправить POST запрос на адрес http://127.0.0.1:9090/translate с JSON вида
```json
{
    "sourceLanguage": "ru",
    "targetLanguage": "en",
    "sourceText": "молоко"
}
```