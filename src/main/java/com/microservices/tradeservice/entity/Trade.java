package com.microservices.tradeservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero
    private BigDecimal quantity;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private BigDecimal value;

    private boolean open = true;

    @PastOrPresent
    @Column(updatable = false)
    private LocalDateTime openDate;

    private LocalDateTime closeDate;

    @NotNull
    private Long userId;

    @NotNull
    private String cryptoSymbol;
}
