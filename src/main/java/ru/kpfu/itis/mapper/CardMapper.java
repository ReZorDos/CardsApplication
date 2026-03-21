package ru.kpfu.itis.mapper;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.CreateCardRequest;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.CardProduct;

@Component
public class CardMapper {

    public CardDto toDto(Card card, CardProduct cardProduct) {
        return CardDto.builder()
                .id(card.getId())
                .userId(card.getUserId())
                .plasticName(card.getPlasticName())
                .contractName(card.getContractName())
                .pan(card.get)
                .expDate(card.getExpDate())
                .cardName(card.getCardName())
                .cardProduct(cardProduct)
                .closeFlag(card.isCloseFlag())
                .imageLink(card.getImageLink())
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
                .imageLink(card.getImageLink())
                .build();
    }

    public Card convertCreateCardRequestToCard(CreateCardRequest createCardRequest) {
        return Card.builder()
                .userId(createCardRequest.getUserId())
                .cardProductId(createCardRequest.getCardProductId())
                .contractName(createCardRequest.getContractName())
                .cardName(createCardRequest.getCardName())
                .imageLink(createCardRequest.getImageLink())
                .build();
    }

}
