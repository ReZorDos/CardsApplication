package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.CardProduct;
import ru.kpfu.itis.repository.CardRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CardRowMapper cardRowMapper = new CardRowMapper();
    private final CardProductRowMapper cardProductRowMapper = new CardProductRowMapper();
    private static final String SQL_GET_CARD_BY_ID = "select * from card where id = ?";
    private static final String SQL_ALL_CARD_PRODUCT = "select * from card_product";
    private static final String SQL_SAVE_CARD = """
            insert into card 
            (user_id, card_product_id, plastic_name, exp_date, cvv, contract_name, card_name, open_document_id, close_document_id, image_link)
            values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private static final String SQL_UPDATE_DATE_EXPENSE = "update card set close_flag = true, close_document_id = ? where id = ?";
    private static final String SQL_GET_ALL_CARDS_OF_USER = "select * from card where user_id = ?";

    @Override
    public Optional<Card> findById(UUID cardId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_CARD_BY_ID, cardRowMapper, cardId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CardProduct> findAllCardProducts() {
        try {
            return jdbcTemplate.query(SQL_ALL_CARD_PRODUCT, cardProductRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    @Override
    public Card saveCardOfUser(Card card) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_CARD, new String[] {"id"});
            ps.setObject(1, card.getUserId());
            ps.setObject(2, card.getCardProductId());
            ps.setString(3, card.getPlasticName());
            ps.setString(4, card.getExpDate());
            ps.setInt(5, card.getCvv());
            ps.setString(6, card.getContractName());
            ps.setString(7, card.getCardName());
            ps.setObject(8, card.getOpenDocumentId());
            ps.setObject(9, card.getCloseDocumentId());
            ps.setString(10, card.getImageLink());
            return ps;
        }, keyHolder);
        UUID cardId = (UUID) keyHolder.getKeys().get("id");
        return findById(cardId).get();
    }

    @Override
    public boolean closeCardOfUser(UUID cardId, UUID closeDocumentId) {
        return jdbcTemplate.update(SQL_UPDATE_DATE_EXPENSE, closeDocumentId, cardId) == 1;
    }

    @Override
    public List<Card> findAllCardsOfUser(UUID userId) {
        try {
            return jdbcTemplate.query(SQL_GET_ALL_CARDS_OF_USER, cardRowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    private static final class CardRowMapper implements RowMapper<Card> {

        @Override
        public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Card.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .userId(UUID.fromString(rs.getString("user_id")))
                    .cardProductId(UUID.fromString(rs.getString("card_product_id")))
                    .plasticName(rs.getString("plastic_name"))
                    .expDate(rs.getString("exp_date"))
                    .cvv(rs.getInt("cvv"))
                    .contractName(rs.getString("contract_name"))
                    .cardName(rs.getString("card_name"))
                    .openDocumentId(UUID.fromString(rs.getString("open_document_id")))
                    .closeDocumentId(UUID.fromString(rs.getString("close_document_id")))
                    .closeFlag(rs.getBoolean("close_flag"))
                    .imageLink(rs.getString("image_link"))
                    .build();
        }
    }

    private static final class CardProductRowMapper implements RowMapper<CardProduct> {

        @Override
        public CardProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CardProduct.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .cardName(rs.getString("card_name"))
                    .description(rs.getString("description"))
                    .cardImageLink(rs.getString("card_image_link"))
                    .build();
        }
    }
}
