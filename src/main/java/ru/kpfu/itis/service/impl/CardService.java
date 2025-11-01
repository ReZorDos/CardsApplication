package ru.kpfu.itis.service.impl;

import ru.kpfu.itis.dto.request.CreateCardRequest;
import ru.kpfu.itis.dto.response.CardResponse;

import java.util.UUID;

public interface CardService {
    CardResponse getCardById(UUID cardId);
    CardResponse openCard(CreateCardRequest request);
    void closeCard(UUID cardId);
}