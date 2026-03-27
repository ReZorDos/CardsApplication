package ru.kpfu.itis.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferDto {

    private String contractName;
    private String createDate;
    private String balance;

}
