package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentRequestDto {

    private String docType;
    private UUID userId;
    private String userFio;
    private String cardNumber;

}
