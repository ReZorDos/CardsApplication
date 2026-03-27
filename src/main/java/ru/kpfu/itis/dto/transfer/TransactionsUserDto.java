package ru.kpfu.itis.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionsUserDto {

    private String contractName;
    private String from;
    private String to;
    private List<TransactionDto> transactions;

}
