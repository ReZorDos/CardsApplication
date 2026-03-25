package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.*;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.service.MockService;
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
    public ResponseEntity<?> createCard(@RequestBody CreateCardRequest createCardRequest) {
        try {
            Card card = cardService.convertCreateRequestToCardEntity(createCardRequest);
            Optional<UserResponseDto> user = apiCallResponse.getUser(card.getUserId());
            if (user.isEmpty()) {
                return new ResponseEntity<>("NO SUCH USER", HttpStatus.NOT_FOUND);
            }
            String pan = cardService.createPan();
            Optional<DocumentResponseDto> documentResponseDto = apiCallResponse.createDocument(
                    card.getUserId(),
                    user.get().getFio(),
                    pan,
                    "CARD_OPENED"
            );
            Optional<TransferDto> transferDto = apiCallResponse.getTransfer();
            if (documentResponseDto.isPresent() && transferDto.isPresent()) {
                CardDto cardResult = cardService.saveCard(
                        card,
                        documentResponseDto.get().getId(),
                        pan,
                        user.get().getFio(),
                        transferDto.get().getContractName()
                );
                return new ResponseEntity<>(cardResult, HttpStatus.CREATED);
            }
            return new ResponseEntity<>("Не смог получить документ или контракт", HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для создания карты: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage(), e);
            return new ResponseEntity<>("Bratan Ya Ne Znay chto y teby tam", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> closeCard(@PathVariable("cardId") UUID cardId) {
        try {
            Optional<Card> card = cardService.getCardByCardId(cardId);
            if (card.isPresent()) {
                Optional<DocumentResponseDto> documentResponseDto = apiCallResponse.createDocument(card.get().getUserId(),
                        card.get().getPlasticName(),
                        card.get().getPan(),
                        "CARD_CLOSED"
                );
                if (documentResponseDto.isPresent()) {
                     cardService.closeCard(cardId, documentResponseDto.get().getId());
                     return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Не получилось достать документ", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("Нет такой карты", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для создания карты: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage(), e);
            return new ResponseEntity<>("Внутренняя ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
