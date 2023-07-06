package com.microservices.tradeservice.controller;

import com.microservices.tradeservice.dto.TradeDealDto;
import com.microservices.tradeservice.dto.TradeDto;
import com.microservices.tradeservice.dto.CreateTradeDto;
import com.microservices.tradeservice.entity.Trade;
import com.microservices.tradeservice.exception.NotEnoughFoundsException;
import com.microservices.tradeservice.exception.TradeNotFoundException;
import com.microservices.tradeservice.exception.TradeWithYourselfException;
import com.microservices.tradeservice.mapper.TradeMapper;
import com.microservices.tradeservice.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;
    private final TradeMapper tradeMapper;

    @GetMapping
    public ResponseEntity<List<TradeDto>> fetchOpenTradesOptionalBySymbol
            (@RequestParam(value = "symbol", required = false) String symbol) {
        List<Trade> trades = tradeService.findOpenTradesOptionalBySymbol(symbol);
        return ResponseEntity.ok(tradeMapper.mapToTradeDtoList(trades));
    }

    @GetMapping(params = "userId")
    public ResponseEntity<List<TradeDto>> fetchTradesByUserIdOptionalWithClosed(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "showClosed", required = false, defaultValue = "false") boolean showClosed) {
        List<Trade> trades = tradeService.findAllByUserId(userId, showClosed);
        return ResponseEntity.ok(tradeMapper.mapToTradeDtoList(trades));
    }

    @PostMapping
    public ResponseEntity<TradeDto> createTrade(@Valid @RequestBody CreateTradeDto createTradeDto) {
        var trade = tradeService.validateAndCreateTrade(createTradeDto);
        return ResponseEntity.ok(tradeMapper.mapToTradeDto(trade));
    }

    @DeleteMapping("/{tradeId}/delete")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long tradeId) throws TradeNotFoundException {
        tradeService.deleteTrade(tradeId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{tradeId}")
    public ResponseEntity<CreateTradeDto> executeTrade (
            @PathVariable Long tradeId,
            @RequestBody TradeDealDto tradeDealDto) throws TradeNotFoundException, NotEnoughFoundsException, TradeWithYourselfException {
        tradeService.executeTransaction(tradeId, tradeDealDto);
        return ResponseEntity.ok().build();
    }
}

