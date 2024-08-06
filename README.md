# translator_2024
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sergeloie_translator_2024&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=sergeloie_translator_2024)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=sergeloie_translator_2024&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=sergeloie_translator_2024)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=sergeloie_translator_2024&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=sergeloie_translator_2024)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=sergeloie_translator_2024&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=sergeloie_translator_2024)

Приложение реализует перевод текста с помощью API Yandex Translate

Так как для доступа к API Yandex Translate требуются ключи и для удобства запуска, приложение упковано в Docker.  
Для запуска приложения необходимо скачать образ  
```shell
docker pull anseranser/translator_2024:v2
```

и запустить его
```shell
docker run -d -p 9090:9090 --name=translator_2024 anseranser/translator_2024:v2
```

Можно открыть простой фронтенд и проверить работу
http://127.0.0.1:9090

или отправить запрос на перевод текста с помощью curl
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
