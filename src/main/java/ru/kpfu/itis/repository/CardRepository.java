package ru.kpfu.itis.repository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository {

    Optional<Card> getCardById(UUID cardId);
    List<CardProduct> getAllCardProduct();
    void saveCardOfUser(Card card);
    boolean closeCardOfUser(UUID cardId);
    Card getInformationOfCard(UUID cardId);
    String getContractName(String cardName);
    List<Card> getCardsByUserId(UUID userId);

}
