package ru.kpfu.itis.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class CreateCardRequest {
    private UUID userId;
    private UUID cardProductId;
    private String contractName;
}