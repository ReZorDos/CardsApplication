package ru.kpfu.itis.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data

@AllArgsConstructor
@Builder
public class StatementRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}