package ru.kpfu.itis.dto;

import lombok.*;
import ru.kpfu.itis.model.CardProduct;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {

    private UUID id;

    /**
     * id пользователя
     */
    private UUID userId;

    /**
     * Имя владельца карты
     */
    private String plasticName;

    /**
     * Договор на карту
     */
    private String contractName;

    /**
     * 16-ти значный номер карты
     */
    private String pan;

    /**
     * дата истечения карты
     */
    private String expDate;

    /**
     * 3 цифры с оборота карты
     */
    private String cvv;

    /**
     * id документа о открытии карты
     */
    private UUID openDocumentId;

    /**
     * id документа о закрытии карты
     */
    private UUID closeDocumentId;

    /**
     * Тип карты
     */
    private CardProduct cardProduct;

    /**
     * Закрыта ли карта
     */
    private boolean closeFlag;

}
