package ru.kpfu.itis.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class CardResponse {
    private boolean success;
    private String message;
    private UUID id;
    private String plasticName;
    private String expDate;
    private int cvv;
}