package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public TransactionsUserDto getTransactions(@PathVariable("cardId") String cardId,
                                             @RequestParam("from") String from,
                                             @RequestParam("to") String to) {
        log.info("Иду проверять наличие карты в БД");
        Optional<CardDto> card = cardService.getCardByContractId(cardId);
        log.info("Я нашел карту в БД: {}", cardId);
        Optional<TransactionsUserDto> transaction = apiCallResponse.getTransactionsOfUser(card.get().getContractName());
        log.info("Сходил в другой микросервис");
        log.info("Получил контракт пользователя");
        TransactionsUserDto transactionsUser = cardService.filterTransactionsByDate(transaction.get(), from, to);
        log.info("Отфильтровал транзации по датам: {}, {}", from, to);
        return transactionsUser;
    }

}
