package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.DocumentDto;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.service.impl.ApiCallResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/v2/cards")
@RequiredArgsConstructor
public class CardInfoController {

    private final CardService cardService;
    private final ApiCallResponse apiCallResponse;

    @GetMapping("/by-id/{id}")
    public ResponseEntity<CardDto> getCardInfoByCardId(@PathVariable("id") UUID id) {
        try {
            Optional<CardDto> cardDtoOpt = cardService.getCardDtoByCardId(id);
            if (cardDtoOpt.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            CardDto cardDto = cardDtoOpt.get();

            Optional<DocumentDto> openDoc = apiCallResponse.getDocument(id, "open");
            openDoc.ifPresent(cardDto::setOpenDocument);

            Optional<DocumentDto> closeDoc = apiCallResponse.getDocument(id, "close");
            closeDoc.ifPresent(cardDto::setCloseDocument);
            return ResponseEntity.ok(cardDto);
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для получения карты: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Card>> getCardsInfoByUserId(@PathVariable("userId") UUID userId) {
        try {
            List<Card> cardDtoList = cardService.getAllCardsOfUser(userId);
            if (cardDtoList.isEmpty()) {
                return ResponseEntity.ok(List.of());
            }
            return ResponseEntity.ok(cardDtoList);
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для получения карты: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-pan/{panId}")
    public ResponseEntity<Card> getCardsInfoByUserId(@PathVariable("panId") String panId) {
        try {
            Optional<Card> card = cardService.getCardByPlasticName(panId);
            if (card.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(card.get());
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для получения карты: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-contract/{contractId}")
    public ResponseEntity<Card> getCardInfoByContractId(@PathVariable("contractId") String contractId) {
        try {
            Optional<Card> card = cardService.getCardByContractId(contractId);
            if (card.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(card.get());
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для получения карты: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
