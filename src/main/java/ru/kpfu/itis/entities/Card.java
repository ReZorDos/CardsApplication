package ru.kpfu.itis.entities;

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

    //TODO: спросить что есть документ у documents
    //TODO: получать эти данные из другого микросервиса
    private String openDocument;
    private String closeDocument;
}

