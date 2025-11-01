package ru.kpfu.itis.service.impl;

import java.util.UUID;

public interface AccountService {
    Object getAccountByCardId(UUID cardID);
}
