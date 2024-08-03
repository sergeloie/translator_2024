package ru.anseranser.translator_2024.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.anseranser.translator_2024.model.CompletedRequest;

@Repository
@RequiredArgsConstructor
public class CompletedRequestRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(CompletedRequest completedRequest) {
        String sql = "INSERT INTO completed_request (ip_address, source_text, translated_text) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                sql,
                completedRequest.getIpAddress(),
                completedRequest.getSourceText(),
                completedRequest.getTranslatedText());
    }
}
