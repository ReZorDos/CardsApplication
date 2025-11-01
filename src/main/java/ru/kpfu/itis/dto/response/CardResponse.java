package ru.kpfu.itis.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CardResponse {
    private boolean sucsess;
    private String message;
    private UUID id;
    private UUID userId;
    private String plasticName;
    private String expDate;
    private String cardName;
    private String contactName;
}
