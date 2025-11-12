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
public class CardDto {

    private UUID userId;
    private UUID cardProductId;
    private String plasticName;
    private String expDate;
    private int cvv;
    private String contractName;
    private String openDocument;
    private String closeDocument;

}
