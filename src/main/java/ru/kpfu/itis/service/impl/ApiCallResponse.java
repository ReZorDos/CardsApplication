package ru.kpfu.itis.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.dto.DocumentRequestDto;
import ru.kpfu.itis.dto.DocumentResponseDto;
import ru.kpfu.itis.dto.TransferDto;
import ru.kpfu.itis.dto.UserResponseDto;
import ru.kpfu.itis.util.PropertyReader;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiCallResponse {

    private final RestTemplate restTemplate;

    private static final String API_DOCUMENTS = PropertyReader.getProperties("API.DOCUMENTS");
    private static final String API_USERS = PropertyReader.getProperties("API.USERS");
    private static final String API_TRANSFERS = PropertyReader.getProperties("API.TRANSFERS");

    public Optional<DocumentResponseDto> createDocument(UUID userId, String userFio, String cardNumber, String type) {
        try {
            DocumentRequestDto document = DocumentRequestDto.builder()
                    .docType(type)
                    .userId(userId)
                    .userFio(userFio)
                    .cardNumber(cardNumber)
                    .build();
            log.info("Отправляем запрос во внешний API: userId={}, userFio={}, cardNumber={}, type={}",
                    userId, userFio, cardNumber, type);

            ResponseEntity<DocumentResponseDto> response = restTemplate.postForEntity(API_DOCUMENTS, document, DocumentResponseDto.class);

            log.info("Ответ от внешнего API: status={}", response.getStatusCode());

            if (response.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(response.getBody());
            }
            return Optional.empty();
        } catch (HttpClientErrorException e) {
            log.info("Документ не найден. Status: {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return Optional.empty();
        } catch (Exception e) {
            log.info("Не получилось достать документ: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<UserResponseDto> getUser(UUID userId) {
        try {
            ResponseEntity<UserResponseDto> response = restTemplate.getForEntity(API_USERS + userId, UserResponseDto.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(response.getBody());
            }
            return Optional.empty();
        } catch (HttpClientErrorException e) {
            log.info("Документ не найден. Status: {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return Optional.empty();
        } catch (Exception e) {
            log.info("Не получилось достать документ: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<TransferDto> getTransfer() {
        try {
            log.info("Отправляем запрос во внешний API: transfer");
            ResponseEntity<TransferDto> transfer = restTemplate.postForEntity(API_TRANSFERS, null, TransferDto.class);
            if (transfer.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(transfer.getBody());
            }
            return Optional.empty();
        } catch (HttpClientErrorException e) {
            log.info("Контракт не найден. Status: {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
            return Optional.empty();
        } catch (Exception e) {
            log.info("Не получилось достать контракт: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
