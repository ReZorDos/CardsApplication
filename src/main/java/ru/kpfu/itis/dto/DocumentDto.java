package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDto {

    private UUID id;
    private String docType;
    private Instant createdDate;
    private UUID userId;
    private String userFio;
    private String cardNumber;

}
