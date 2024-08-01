package ru.anseranser.translator_2024.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "completed_request")
public class CompletedRequest {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "ip_address", length = 15)
    private String ipAddress;


    @Column(name = "source_text")
    private String sourceText;

    @Column(name = "translated_text")
    private String translatedText;
}