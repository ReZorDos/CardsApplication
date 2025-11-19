package ru.kpfu.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    
    private UUID id;
    private UUID userId;
    private UUID cardProductId;
    private String plasticName;
    private String expDate;
    private int cvv;
    private String contractName;
    private String cardName;
    private UUID openDocumentId;
    private UUID closeDocumentId;
    private boolean closeFlag;
}

