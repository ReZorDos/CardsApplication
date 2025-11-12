package ru.kpfu.itis.service.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.mapper.CardMapper;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.CardProduct;
import ru.kpfu.itis.repository.CardRepository;
import ru.kpfu.itis.service.CardService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Override
    public Optional<CardDto> getCardByCardIdAndUserId(UUID cardId, UUID userId) {
        Optional<Card> cardOptional = cardRepository.findCardByCardIdAndUserId(cardId, userId);
        return cardOptional.map(cardMapper::toDto);
    }

    @Override
    public List<CardProduct> getAllCardProduct() {
        return cardRepository.findAllCardProducts();
    }

    @Override
    public CardDto saveCard(Card card) {
        Card cardResponse = cardRepository.saveCardOfUser(card);
        return cardMapper.toDto(cardResponse);
    }

    @Override
    public boolean closeCard(UUID cardId) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            return cardRepository.closeCardOfUser(cardId);
        } else {
            return false;
        }
    }

    @Override
    public Card getAccountStatement(UUID cardId) {
        return null;
    }

    @Override
    public String getContractName() {
        return "";
    }
}
