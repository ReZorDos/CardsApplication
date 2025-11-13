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
public class CreateCardRequest {

    private UUID userId;
    private UUID cardProductId;
    private String contractName;
    private String cardName;
    private String openDocument;
    private String closeDocument;

}
