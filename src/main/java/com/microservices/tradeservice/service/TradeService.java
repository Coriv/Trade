package com.microservices.tradeservice.service;

import com.microservices.tradeservice.config.UrlConfig;
import com.microservices.tradeservice.dto.CreateTradeDto;
import com.microservices.tradeservice.dto.TradeDealDto;
import com.microservices.tradeservice.entity.Trade;
import com.microservices.tradeservice.exception.NotEnoughFoundsException;
import com.microservices.tradeservice.exception.TradeNotFoundException;
import com.microservices.tradeservice.exception.TradeWithYourselfException;
import com.microservices.tradeservice.repository.TradeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeDao tradeDao;
    private final RestTemplate restTemplate;
    private final UrlConfig urlConfig;

    public List<Trade> findOpenTrades(String symbol) {
        List<Trade> trades = tradeDao.findAllByOpenIsTrue();
        if (symbol != null) {
            trades = trades.stream()
                    .filter(trade -> trade.getCryptoSymbol().equals(symbol))
                    .collect(Collectors.toList());
        }
        return trades;
    }

    public List<Trade> findAllByUserId(Long userId, boolean showClosed) {
        var trades = tradeDao.findAllByUserId(userId);
        if (showClosed == false) {
            return trades.stream()
                    .filter(t -> t.isOpen())
                    .collect(Collectors.toList());
        }
        return trades;
    }

    public Trade createNewTrade(CreateTradeDto createTradeDto) {
        Trade trade = new Trade();
        trade.setUserId(trade.getUserId());
        trade.setCryptoSymbol(createTradeDto.getCryptoSymbol());
        trade.setQuantity(createTradeDto.getQuantity());
        trade.setPrice(trade.getPrice());
        trade.setValue(createTradeDto.getPrice().multiply(createTradeDto.getQuantity()));
        trade.setOpenDate(LocalDateTime.now());
        return tradeDao.save(trade);
    }

    public void deleteTrade(Long tradeId) throws TradeNotFoundException {
        var trade = tradeDao.findById(tradeId).orElseThrow(TradeNotFoundException::new);
        updateCryptoWallet(
                TradeDealDto.builder()
                .userId(trade.getUserId())
                .symbol(trade.getCryptoSymbol())
                .quantity(trade.getQuantity())
                .build());
        tradeDao.delete(trade);
    }

    public void executeTransaction(Long tradeId, TradeDealDto tradeDealDto) throws TradeNotFoundException, NotEnoughFoundsException, TradeWithYourselfException {
        var trade = tradeDao.findById(tradeId).orElseThrow(TradeNotFoundException::new);
        var transactionValue = tradeDealDto.getQuantity().multiply(trade.getPrice());
        validateTrade(trade, tradeDealDto);
        updateTrade(trade, transactionValue);
        updateUserCashWallet(trade.getUserId(), transactionValue);
        updateCryptoWallet(tradeDealDto);
    }

    private void validateTrade(Trade trade, TradeDealDto tradeDealDto) throws NotEnoughFoundsException, TradeWithYourselfException {
        if (trade.getQuantity().compareTo(tradeDealDto.getQuantity()) < 0)
            throw new NotEnoughFoundsException();
        if (trade.getUserId().equals(tradeDealDto.getUserId()))
        throw new TradeWithYourselfException();
    }

    private void updateTrade(Trade trade, BigDecimal transactionValue) {
        trade.setQuantity(trade.getQuantity().subtract(transactionValue));
        trade.setValue(trade.getQuantity().multiply(trade.getPrice()));
        tradeDao.save(trade);
    }

    private void updateUserCashWallet(long userId, BigDecimal value) {
        var dto = TradeDealDto.builder()
                .userId(userId)
                .quantity(value)
                .symbol("$ USD")
                .build();
        //todo consider asynchronous communication here
        restTemplate.put(urlConfig.getWalletDepositUrl(), dto);
    }

    private void updateCryptoWallet(TradeDealDto tradeDealDto) {
        restTemplate.put(urlConfig.getWalletCryptoDepositUrl(), tradeDealDto);
    }
}
