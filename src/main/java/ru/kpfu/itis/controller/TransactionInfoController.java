package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.card.CardDto;
import ru.kpfu.itis.dto.transfer.TransactionsUserDto;
import ru.kpfu.itis.service.CardService;
import ru.kpfu.itis.service.impl.ApiCallResponse;

import java.util.Optional;

@RestController
@RequestMapping("api/v2/cards")
@RequiredArgsConstructor
@Slf4j
public class TransactionInfoController {

    private final ApiCallResponse apiCallResponse;
    private final CardService cardService;

    @GetMapping("/statement/{cardId}")
    public ResponseEntity<?> getTransactions(@PathVariable("cardId") String cardId,
                                             @RequestParam("from") String from,
                                             @RequestParam("to") String to) {
        try {
            Optional<CardDto> card = cardService.getCardByContractId(cardId);
            if (card.isPresent()) {
                Optional<TransactionsUserDto> transaction = apiCallResponse.getTransactionsOfUser(card.get().getContractName());
                if (transaction.isPresent()) {
                    TransactionsUserDto transactionsUser = cardService.filterTransactionsByDate(transaction.get(), from, to);
                    return new ResponseEntity<>(transactionsUser, HttpStatus.OK);
                }
                return new ResponseEntity<>("Unable to retrieve transactions", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            log.error("Некорректные данные для получения транзакций: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Неожиданная ошибка: {}", e.getMessage(), e);
            return new ResponseEntity<>("Server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
