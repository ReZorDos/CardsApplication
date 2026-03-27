package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.dto.card.CardDto;
import ru.kpfu.itis.service.CardService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/v2/cards")
@RequiredArgsConstructor
public class CardInfoController {

    private final CardService cardService;

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> getCardInfoByCardId(@PathVariable("id") UUID id) {
        try {
            Optional<CardDto> cardDto = cardService.getCardDtoByCardId(id);
            if (cardDto.isEmpty()) {
                return new ResponseEntity<>("Карта не найдена в базе данных", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(cardDto.get());
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для получения карты: {}", e.getMessage());
            return new ResponseEntity<>("Некорректные данные для получения карты", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage());
            return new ResponseEntity<>("Ошибка сервера:" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getCardsInfoByUserId(@PathVariable("userId") UUID userId) {
        try {
            List<CardDto> cardDtoList = cardService.getAllCardsOfUser(userId);
            if (cardDtoList.isEmpty()) {
                return ResponseEntity.ok(List.of());
            }
            return ResponseEntity.ok(cardDtoList);
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для получения карты: {}", e.getMessage());
            return new ResponseEntity<>("Некорректные данные для получения карты", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage());
            return new ResponseEntity<>("Ошибка сервера: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-pan/{panId}")
    public ResponseEntity<?> getCardsInfoByUserId(@PathVariable("panId") String panId) {
        try {
            Optional<CardDto> card = cardService.getCardByPan(panId);
            if (card.isEmpty()) {
                return new ResponseEntity<>("Карта не найдена в базе данных", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(card.get());
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для получения карты: {}", e.getMessage());
            return new ResponseEntity<>("Некорректные данные для получения карты", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage());
            return new ResponseEntity<>("Ошибка сервера: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-contract/{contractId}")
    public ResponseEntity<?> getCardInfoByContractId(@PathVariable("contractId") String contractId) {
        try {
            Optional<CardDto> card = cardService.getCardByContractId(contractId);
            if (card.isEmpty()) {
                return new ResponseEntity<>("Карта не найдена в базе данных", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(card.get());
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для получения карты: {}", e.getMessage());
            return new ResponseEntity<>("Некорректные данные для получения карты", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage());
            return new ResponseEntity<>("Ошибка сервера: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
