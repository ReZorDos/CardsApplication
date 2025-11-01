package ru.kpfu.itis.service.impl;

import ru.kpfu.itis.dto.request.StatementRequest;
import ru.kpfu.itis.dto.response.StatementResponse;

import java.util.UUID;

public interface StatementService {
    StatementResponse getCardStatment(UUID cardId, StatementRequest request);
}
