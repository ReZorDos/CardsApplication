package ru.kpfu.itis.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.transfer.TransactionRequest;
import ru.kpfu.itis.dto.transfer.TransactionsUserDto;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.service.impl.ApiCallResponse;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v2/cards")
@RequiredArgsConstructor
@Slf4j
public class TransactionInfoController {

    private final ApiCallResponse apiCallResponse;
    private final CardService cardService;

    @GetMapping("/statement/{cardId}")
    public ResponseEntity<?> getTransactions(@RequestBody TransactionRequest transactionRequest,
                                             @PathVariable("cardId") UUID cardId) {
        try {
            Optional<Card> card = cardService.getCardByCardId(cardId);
            if (card.isPresent()) {
                Optional<TransactionsUserDto> transaction = apiCallResponse.getTransactionsOfUser(card.get().getId());
                if (transaction.isPresent()) {
                    return new ResponseEntity<>(transaction.get(), HttpStatus.OK);
                }
                return new ResponseEntity<>("Не получилось достать транзакции", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>("Нет такого пользователя", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для получения транзакций: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage(), e);
            return new ResponseEntity<>("Ошибка сервера: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
