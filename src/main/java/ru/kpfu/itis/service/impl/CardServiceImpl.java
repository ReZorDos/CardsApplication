package ru.kpfu.itis.service.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.CreateCardRequest;
import ru.kpfu.itis.dto.DocumentDto;
import ru.kpfu.itis.mapper.CardMapper;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.CardProduct;
import ru.kpfu.itis.repository.CardRepository;
import ru.kpfu.itis.service.CardService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            return Optional.ofNullable(cardMapper.toDtoWithoutDocument(cardOptional.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<CardProduct> getAllCardProduct() {
        return cardRepository.findAllCardProducts();
    }

    @Override
    public CardDto saveCard(Card card, DocumentDto documentDto) {
        card.setPlasticName(String.valueOf(ThreadLocalRandom.current().nextLong(1000000000000000L, 9999999999999999L)));

        LocalDate expDate = LocalDate.now().plusYears(10);
        card.setExpDate(String.valueOf(expDate));

        card.setCvv(random.nextInt(900) + 100);

        card.setOpenDocumentId(documentDto.getId());

        Card cardResponse = cardRepository.saveCardOfUser(card);
        return cardMapper.toDto(cardResponse, documentDto);
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
}
