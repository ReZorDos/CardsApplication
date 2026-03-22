package ru.kpfu.itis.service;

import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.CreateCardRequest;
import ru.kpfu.itis.dto.DocumentResponseDto;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.CardProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardService {

    Optional<Card> getCardByCardId(UUID cardId);

    Optional<CardDto> getCardDtoByCardId(UUID cardId);

    List<CardProduct> getAllCardProduct();

    CardDto saveCard(Card card, UUID documentOpenDto, String pan, String fio);

    boolean closeCard(UUID cardId, UUID closeDocumentId);

    Card convertCreateRequestToCardEntity(CreateCardRequest cardRequest);

    List<CardDto> getAllCardsOfUser(UUID userId);

    Optional<CardProduct> getCardProductById(UUID id);

    Optional<CardDto> getCardByPan(String plasticName);

    Optional<CardDto> getCardByContractId(String contractId);

    String createPan();
}
