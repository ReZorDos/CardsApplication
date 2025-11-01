package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.repository.CardRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepository {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Card> getCardById(UUID cardId) {
        return Optional.empty();
    }

    @Override
    public List<CardProduct> getAllCardProduct() {
        return null;
    }

    @Override
    public void saveCardOfUser(Card card) {

    }

    @Override
    public boolean closeCardOfUser(UUID cardId) {
        return false;
    }

    @Override
    public Card getInformationOfCard(UUID cardId) {
        return null;
    }

    @Override
    public String getContractName(String cardName) {
        return "";
    }

    @Override
    public List<Card> getCardsByUserId(UUID userId) {
        return null;
    }
}
