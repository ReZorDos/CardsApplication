package ru.kpfu.itis.dto.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
