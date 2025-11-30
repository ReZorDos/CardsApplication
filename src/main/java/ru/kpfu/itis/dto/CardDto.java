package ru.kpfu.itis.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {

    private UUID userId;
    private UUID cardProductId;
    private String plasticName;
    private String expDate;
    private String contractName;
    private String cardName;
    private DocumentDto openDocument;
    private DocumentDto closeDocument;
    private boolean closeFlag;
    private String imageLink;

}
