package com.microservices.tradeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateTradeDto {
    @NotNull
    private Long userId;
    @NotNull
    private String cryptoSymbol;
    @NotNull
    private BigDecimal quantity;
    @NotNull
    private BigDecimal price;
}
