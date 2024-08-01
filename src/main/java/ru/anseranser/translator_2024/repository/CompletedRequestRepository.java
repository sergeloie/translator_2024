package ru.anseranser.translator_2024.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anseranser.translator_2024.model.CompletedRequest;

public interface CompletedRequestRepository extends JpaRepository<CompletedRequest, Long> {
}