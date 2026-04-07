package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public CardDto getCardInfoByCardId(@PathVariable("id") UUID id) {
        Optional<CardDto> cardDto = cardService.getCardDtoByCardId(id);
        log.info("Получил карту по id: {}", id);
        return cardDto.get();
    }

    @GetMapping("/by-user/{userId}")
    public List<CardDto> getCardsInfoByUserId(@PathVariable("userId") UUID userId) {
        List<CardDto> cardDtoList = cardService.getAllCardsOfUser(userId);
        log.info("Получил карту по userId: {}", userId);
        return cardDtoList;
    }

    @GetMapping("/by-pan/{panId}")
    public CardDto getCardsInfoByUserId(@PathVariable("panId") String panId) {
        Optional<CardDto> card = cardService.getCardByPan(panId);
        log.info("Получил карту по pan: {}", panId);
        return card.get();
    }

    @GetMapping("/by-contract/{contractId}")
    public CardDto getCardInfoByContractId(@PathVariable("contractId") String contractId) {
        Optional<CardDto> card = cardService.getCardByContractId(contractId);
        log.info("Получил карту по contractId: {}", contractId);
        return card.get();
    }

}
