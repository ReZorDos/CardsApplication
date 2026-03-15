package ru.kpfu.itis.service.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.dto.DocumentDto;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiCallResponse {

    private final RestTemplate restTemplate;

    private static final String API_GET_DOCUMENT = "IP_ДОКУМЕНТОВ/docks/api/documents/";

    public Optional<DocumentDto> getDocument(UUID id, String typeDocument) {
        String apiUrl = String.join(API_GET_DOCUMENT, typeDocument, "/", String.valueOf(id));
        try {
            DocumentDto documentDto = restTemplate.getForObject(apiUrl, DocumentDto.class, id);
            if (documentDto != null) {
                return Optional.of(documentDto);
            }
            return Optional.empty();
        } catch (HttpClientErrorException e) {
            log.info("Документ не найден");
            return Optional.empty();
        } catch (Exception e) {
            log.info("Не получилось достать документ: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }



}
