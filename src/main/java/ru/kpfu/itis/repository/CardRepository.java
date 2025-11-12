package ru.kpfu.itis.repository;

import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.CardProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository {

    Optional<Card> findById(UUID cardId);

    Optional<Card> findCardByCardIdAndUserId(UUID cardId, UUID user_id);

    List<CardProduct> findAllCardProducts();

    Card saveCardOfUser(Card card);

    boolean closeCardOfUser(UUID cardId);

    //FIXME: понять последние 2 метода
    //получение выписки по карте. Просто отдаем карту другому микросервису
    Card findAccountStatementOfCard(UUID cardId);

    //получение счета по номеру карты
    String findContractName(UUID cardId);

}
