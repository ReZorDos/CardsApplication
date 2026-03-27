package ru.kpfu.itis.mapper;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.card.CardDto;
import ru.kpfu.itis.dto.card.CreateCardRequest;
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
                .pan(card.getPan())
                .expDate(card.getExpDate())
                .cvv(String.valueOf(card.getCvv()))
                .openDocumentId(card.getOpenDocumentId())
                .closeDocumentId(card.getCloseDocumentId())
                .cardProduct(cardProduct)
                .closeFlag(card.isCloseFlag())
                .build();
    }

    public Card convertCreateCardRequestToCard(CreateCardRequest createCardRequest) {
        return Card.builder()
                .userId(createCardRequest.getUserId())
                .cardProductId(createCardRequest.getCardProductId())
                .build();
    }

}
