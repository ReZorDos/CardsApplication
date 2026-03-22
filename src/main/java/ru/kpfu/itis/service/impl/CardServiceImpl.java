package ru.kpfu.itis.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.CreateCardRequest;
import ru.kpfu.itis.mapper.CardMapper;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.CardProduct;
import ru.kpfu.itis.repository.CardRepository;
import ru.kpfu.itis.service.CardService;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final Random random = new Random();
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Override
    public Optional<Card> getCardByCardId(UUID cardId) {
        return cardRepository.findById(cardId);
    }

    @Override
    public Optional<CardDto> getCardDtoByCardId(UUID cardId) {
        Optional<Card> card = cardRepository.findById(cardId);
        if (card.isPresent()) {
            CardProduct cardProduct = cardRepository.findCardProductById(card.get().getCardProductId()).orElseThrow();
            return Optional.ofNullable(cardMapper.toDto(card.get(), cardProduct));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<CardProduct> getAllCardProduct() {
        return cardRepository.findAllCardProducts();
    }

    @Override
    public CardDto saveCard(Card card, UUID documentOpenDto, String pan, String fio) {
        card.setPan(pan);

        card.setPlasticName(fio);
        card.setContractName(String.valueOf(random.nextInt(100000, 1000000)));

        LocalDate expDate = LocalDate.now().plusYears(10);
        card.setExpDate(String.valueOf(expDate));

        card.setCvv(random.nextInt(900) + 100);

        card.setOpenDocumentId(documentOpenDto);

        Card cardResponse = cardRepository.saveCardOfUser(card);
        CardProduct cardProduct = cardRepository.findCardProductById(card.getCardProductId())
                .orElseThrow(() -> new RuntimeException("Card product not found"));
        return cardMapper.toDto(cardResponse, cardProduct);
    }

    @Override
    public boolean closeCard(UUID cardId, UUID closeDocumentId) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            return cardRepository.closeCardOfUser(cardId, closeDocumentId);
        } else {
            return false;
        }
    }

    @Override
    public Card convertCreateRequestToCardEntity(CreateCardRequest cardRequest) {
        return cardMapper.convertCreateCardRequestToCard(cardRequest);
    }

    @Override
    public List<CardDto> getAllCardsOfUser(UUID userId) {
        return cardRepository.findAllCardsOfUser(userId).stream()
                .map(card -> {
                    CardProduct cardProduct = cardRepository.findCardProductById(card.getCardProductId())
                            .orElseThrow(() -> new RuntimeException("Card product not found"));
                    return cardMapper.toDto(card, cardProduct);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CardProduct> getCardProductById(UUID id) {
        return cardRepository.findCardProductById(id);
    }

    @Override
    public Optional<CardDto> getCardByPan(String pan) {
        Optional<Card> card = cardRepository.findCardByPan(pan);
        if (card.isPresent()) {
            CardProduct cardProduct = cardRepository.findCardProductById(card.get().getCardProductId()).orElseThrow();
            return Optional.ofNullable(cardMapper.toDto(card.get(), cardProduct));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CardDto> getCardByContractId(String contractId) {
        Optional<Card> card = cardRepository.findCardByContractId(contractId);
        if (card.isPresent()) {
            CardProduct cardProduct = cardRepository.findCardProductById(card.get().getCardProductId()).orElseThrow();
            return Optional.ofNullable(cardMapper.toDto(card.get(), cardProduct));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String createPan() {
        return String.valueOf(ThreadLocalRandom.current().nextLong(1000000000000000L, 9999999999999999L));
    }
}
