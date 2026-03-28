package ru.kpfu.itis.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

    private String sourceContractId;
    private String targetContractId;
    private String amount;
    private String description;
    private String createdAt;

}
