package ru.kpfu.itis.service;

import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.CardProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardService {

    Optional<CardDto> getCardByCardIdAndUserId(UUID cardId, UUID userId);

    List<CardProduct> getAllCardProduct();

    CardDto saveCard(Card card);

    boolean closeCard(UUID cardId);

    Card getAccountStatement(UUID cardId);

    String getContractName();

}
