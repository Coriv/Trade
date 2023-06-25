package com.microservices.tradeservice.mapper;

import com.microservices.tradeservice.dto.TradeDto;
import com.microservices.tradeservice.entity.Trade;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TradeMapper {

    public TradeDto mapToTradeDto(Trade trade) {
        return TradeDto.builder()
                .id(trade.getId())
                .quantity(trade.getQuantity())
                .price(trade.getPrice())
                .value(trade.getQuantity().multiply(trade.getPrice()))
                .open(trade.isOpen())
                .openDate(trade.getOpenDate())
                .closeDate(trade.getCloseDate())
                .cryptoSymbol(trade.getCryptoSymbol())
                .userId(trade.getUserId())
                .build();
    }

    public List<TradeDto> mapToTradeDtoList(List<Trade> list) {
        return list.stream()
                .map(this::mapToTradeDto)
                .collect(Collectors.toList());
    }
}
