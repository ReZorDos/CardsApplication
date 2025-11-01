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
public class CardProduct {
    
    private UUID id;
    private String cardName;
    private String description;
    private String cardImageLink;
}

