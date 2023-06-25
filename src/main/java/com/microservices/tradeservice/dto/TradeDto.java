package com.microservices.tradeservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class TradeDto {

    private Long id;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal value;
    private LocalDateTime openDate;
    private LocalDateTime closeDate;
    @NotNull
    private Long userId;
    @NotNull
    private String cryptoSymbol;
}
