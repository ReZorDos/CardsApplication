package ru.kpfu.itis.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class CardProductListResponse {
    private boolean success;
    private String message;
    private List<CardProductDto> products;

    @Data
    @Builder
    public static class CardProductDto {
        private UUID id;
        private String cardName;
        private String description;
        private String cardImageLink;
    }
}