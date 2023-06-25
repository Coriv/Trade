package com.microservices.tradeservice.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TradeDealDto {
    private Long userId;
    private String symbol;
    private BigDecimal quantity;
}
