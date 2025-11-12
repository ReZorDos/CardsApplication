package ru.kpfu.itis.mapper;

import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.model.Card;

public class CardMapper {

    public CardDto toDto(Card card) {
        return CardDto.builder()
                .userId(card.getUserId())
                .cardProductId(card.getCardProductId())
                .plasticName(card.getPlasticName())
                .expDate(card.getPlasticName())
                .cvv(card.getCvv())
                .contractName(card.getContractName())
                .openDocument(card.getOpenDocument())
                .closeDocument(card.getCloseDocument())
                .build();
    }

}
