package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.CreateCardRequest;
import ru.kpfu.itis.dto.DocumentDto;
import ru.kpfu.itis.mapper.CardComponentMapper;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.service.component.ApiCallResponse;

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
            Optional<DocumentDto> documentOpenDto = apiCallResponse.getDocument(createCardRequest.getUserId(), "open");
            if (documentOpenDto.isPresent()) {
                Card card = cardService.convertCreateRequestToCardEntity(createCardRequest);
                cardService.saveCard(card, documentOpenDto.get());
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                log.info("Документ не был получен из запроса");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для создания карты: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage(), e);
            return new ResponseEntity<>("Внутренняя ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable("cardId") UUID cardId) {
        try {
            Optional<DocumentDto> documentCloseDto = apiCallResponse.getDocument(cardId, "close");
            if (documentCloseDto.isPresent()) {
                cardService.closeCard(cardId, documentCloseDto.get().getId());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.info("Документ не был получен из запроса");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
