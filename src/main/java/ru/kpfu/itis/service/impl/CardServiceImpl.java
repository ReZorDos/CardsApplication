package ru.kpfu.itis.service.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.CreateCardRequest;
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
    public Optional<CardDto> getCardByCardId(UUID cardId) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            return Optional.ofNullable(cardMapper.toDto(cardOptional.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<CardProduct> getAllCardProduct() {
        return cardRepository.findAllCardProducts();
    }

    @Override
    public CardDto saveCard(Card card) {
        card.setPlasticName(String.valueOf(ThreadLocalRandom.current().nextLong(1000000000000000L, 9999999999999999L)));

        LocalDate expDate = LocalDate.now().plusYears(10);
        card.setExpDate(String.valueOf(expDate));

        card.setCvv(random.nextInt(900) + 100);

        Card cardResponse = cardRepository.saveCardOfUser(card);
        return cardMapper.toDto(cardResponse);
    }

    @Override
    public boolean closeCard(UUID cardId, String closeDocument) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            return cardRepository.closeCardOfUser(cardId, closeDocument);
        } else {
            return false;
        }
    }


    @Override
    public Card convertCreateRequestToCardEntity(CreateCardRequest cardRequest) {
        return cardMapper.convertCreateCardRequestToCard(cardRequest);
    }
}
