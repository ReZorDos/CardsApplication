package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponseDto {

    private UUID id;
    private String docType;
    private UUID userId;
    private String createdDate;
    private String userFio;
    private String cardNumber;

}
