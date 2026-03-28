package ru.kpfu.itis.dto.transfer;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionsUserDto {

    private String contractName;
    private String from;
    private String to;
    private List<TransactionDto> transactions;

}
