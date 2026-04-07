package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.*;
import ru.kpfu.itis.dto.card.CardDto;
import ru.kpfu.itis.dto.card.CreateCardRequest;
import ru.kpfu.itis.dto.document.DocumentResponseDto;
import ru.kpfu.itis.dto.transfer.TransferDto;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.service.impl.ApiCallResponse;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("api/v2/cards")
@RequiredArgsConstructor
public class CardHandlerController {

    private final CardService cardService;
    private final ApiCallResponse apiCallResponse;

    @PostMapping
    public CardDto createCard(@RequestBody CreateCardRequest createCardRequest) {
        Card card = cardService.convertCreateRequestToCardEntity(createCardRequest);
        Optional<UserResponseDto> user = apiCallResponse.getUser(card.getUserId());
        log.info("Получил юзера из User Microservice");
        String pan = cardService.createPan();
        log.info("Создал pan");
        Optional<DocumentResponseDto> documentResponseDto = apiCallResponse.createDocument(
                card.getUserId(),
                user.get().getFio(),
                pan,
                "CARD_OPENED"
        );
        log.info("Сходил за документом");
        Optional<TransferDto> transferDto = apiCallResponse.getTransfer();
        return cardService.saveCard(
                card,
                documentResponseDto.get().getId(),
                pan,
                user.get().getFio(),
                transferDto.get().getContractName()
        );
    }

    @DeleteMapping("/{cardId}")
    public void closeCard(@PathVariable("cardId") UUID cardId) {
        Optional<Card> card = cardService.getCardByCardId(cardId);
        Optional<DocumentResponseDto> documentResponseDto = apiCallResponse.createDocument(card.get().getUserId(),
            card.get().getPlasticName(),
            card.get().getPan(),
            "CARD_CLOSED"
        );
        cardService.closeCard(cardId, documentResponseDto.get().getId());
    }

}
