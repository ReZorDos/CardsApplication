package ru.kpfu.itis.mapper;

import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.CreateCardRequest;
import ru.kpfu.itis.dto.DocumentDto;
import ru.kpfu.itis.model.Card;

public class CardMapper {

    public CardDto toDto(Card card, DocumentDto documentDto) {
        return CardDto.builder()
                .userId(card.getUserId())
                .cardProductId(card.getCardProductId())
                .plasticName(card.getPlasticName())
                .expDate(card.getExpDate())
                .contractName(card.getContractName())
                .cardName(card.getCardName())
                .openDocument(documentDto.getDocType().equals("CARD_OPENED") ? documentDto : null)
                .closeDocument(documentDto.getDocType().equals("CARD_CLOSED") ? documentDto : null)
                .closeFlag(card.isCloseFlag())
                .build();
    }

    public CardDto toDtoWithoutDocument(Card card) {
        return CardDto.builder()
                .userId(card.getUserId())
                .cardProductId(card.getCardProductId())
                .plasticName(card.getPlasticName())
                .expDate(card.getExpDate())
                .contractName(card.getContractName())
                .cardName(card.getCardName())
                .closeFlag(card.isCloseFlag())
                .build();
    }

    public Card convertCreateCardRequestToCard(CreateCardRequest createCardRequest) {
        return Card.builder()
                .userId(createCardRequest.getUserId())
                .cardProductId(createCardRequest.getCardProductId())
                .contractName(createCardRequest.getContractName())
                .cardName(createCardRequest.getCardName())
                .build();
    }

}
