package ru.kpfu.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.dto.ApiResponse;
import ru.kpfu.itis.dto.CardDto;
import ru.kpfu.itis.dto.DocumentDto;
import ru.kpfu.itis.service.CardService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/cards")
@RequiredArgsConstructor
public class CardInformationController {

    private final CardService cardService;
    private final RestTemplate restTemplate;

    private static final String API_DOCUMENTS_GET_OPEN_DOCUMENT = "IP_ДОКУМЕНТОВ/docks/api/documents/user/open/";
    private static final String API_DOCUMENTS_GET_CLOSE_DOCUMENT = "IP_ДОКУМЕНТОВ/docks/api/documents/user/close/";

    @GetMapping("/get-all-info/{cardId}")
    public ResponseEntity<ApiResponse<CardDto>> getAllInfo(@PathVariable UUID cardId) {
        Optional<CardDto> cardDtoOpt = cardService.getCardDtoByCardId(cardId);
        if (cardDtoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("card not found", null));
        }
        CardDto cardDto = cardDtoOpt.get();

        try {
            DocumentDto openDoc = restTemplate.getForObject(API_DOCUMENTS_GET_OPEN_DOCUMENT + cardId, DocumentDto.class);
            cardDto.setOpenDocument(openDoc);
        } catch (Exception e) {}
        try {
            DocumentDto closeDoc = restTemplate.getForObject(API_DOCUMENTS_GET_CLOSE_DOCUMENT + cardId, DocumentDto.class);
            cardDto.setCloseDocument(closeDoc);
        } catch (Exception e) {}
        return ResponseEntity.ok(new ApiResponse<>("success", cardDto));
    }




}
