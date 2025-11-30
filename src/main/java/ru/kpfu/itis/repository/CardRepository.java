package ru.kpfu.itis.repository;

import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.CardProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository {

    Optional<Card> findById(UUID cardId);

    List<CardProduct> findAllCardProducts();

    Card saveCardOfUser(Card card);

    boolean closeCardOfUser(UUID cardId, UUID closeDocumentId);

    List<Card> findAllCardsOfUser(UUID userId);
}
