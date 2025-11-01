package ru.kpfu.itis.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class StatementResponse {
    private boolean success;
    private String message;
    private UUID cardId;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private List<TransactionDto> transactions;
    private Double totalAmount;

    @Data
    @Builder
    public static class TransactionDto {
        private LocalDate date;
        private Double amount;
        private String description;
        private String type;
    }
}