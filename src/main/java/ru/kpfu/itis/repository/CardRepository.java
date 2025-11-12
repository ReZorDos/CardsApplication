package ru.kpfu.itis.repository;

import ru.kpfu.itis.entities.Card;
import ru.kpfu.itis.entities.CardProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository {

    Optional<Card> getById(UUID cardId);

    Optional<Card> getCardByCardIdAndUserId(UUID cardId, UUID user_id);

    List<CardProduct> getAllCardProducts();

    Card saveCardOfUser(Card card);

    boolean closeCardOfUser(UUID cardId);

    //FIXME: понять последние 2 метода
    //получение выписки по карте. Просто отдаем карту другому микросервису
    Card getAccountStatementOfCard(UUID cardId);

    //получение счета по номеру карты
    String getContractName(UUID cardId);

}
