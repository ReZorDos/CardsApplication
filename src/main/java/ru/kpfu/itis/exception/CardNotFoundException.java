package ru.kpfu.itis.exception;

import java.util.UUID;

public class CardNotFoundException extends NotFoundException {
    public CardNotFoundException(UUID id) {
        super(String.format("Card with this id = %s, not found", id));
    }
}
