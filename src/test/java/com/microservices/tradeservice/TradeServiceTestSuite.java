package com.microservices.tradeservice;

import com.microservices.tradeservice.dto.CreateTradeDto;
import com.microservices.tradeservice.entity.Trade;
import com.microservices.tradeservice.feignClient.WalletCryptoClient;
import com.microservices.tradeservice.repository.TradeDao;
import com.microservices.tradeservice.service.TradeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTestSuite {
    @InjectMocks
    private TradeService service;
    @Mock
    private TradeDao tradeDao;
    @Mock
    private WalletCryptoClient walletCryptoClient;

    @Test
    public void testFindOpenTradesBySymbol() {
        // given
        String symbol = "BTC";
        List<Trade> trades = new ArrayList<>();
        Trade trade1 = new Trade();
        trade1.setCryptoSymbol("BTC");
        trade1.setOpen(true);
        trades.add(trade1);
        Trade trade2 = new Trade();
        trade2.setCryptoSymbol("BTC");
        trade2.setOpen(false);
        trades.add(trade2);

        when(tradeDao.findAllByOpenIsTrue()).thenReturn(trades);
        // when
        List<Trade> result = service.findOpenTradesOptionalBySymbol(symbol);
        // then
        assertEquals(2, result.size());
        assertEquals("BTC", result.get(0).getCryptoSymbol());
        assertEquals("BTC", result.get(1).getCryptoSymbol());
    }

    @Test
    public void testFindAllByUserId() {
        // given
        Long userId = 1L;
        boolean showClosed = false;
        List<Trade> trades = new ArrayList<>();
        Trade trade1 = new Trade();
        trade1.setUserId(userId);
        trade1.setOpen(true);
        trades.add(trade1);
        Trade trade2 = new Trade();
        trade2.setUserId(userId);
        trade2.setOpen(false);
        trades.add(trade2);
        Trade trade3 = new Trade();
        trade3.setUserId(userId);
        trade3.setOpen(true);
        trades.add(trade3);

        when(tradeDao.findAllByUserId(userId)).thenReturn(trades);
        // when
        List<Trade> result = service.findAllByUserId(userId, showClosed);
        // then
        assertEquals(2, result.size());
        assertEquals(userId, result.get(0).getUserId());
        assertTrue(result.get(0).isOpen());
        assertEquals(userId, result.get(1).getUserId());
        assertTrue(result.get(1).isOpen());
    }

    @Test
    public void testValidateAndCreateTrade() {
        // given
        CreateTradeDto createTradeDto = new CreateTradeDto();
        createTradeDto.setCryptoSymbol("BTC");
        createTradeDto.setQuantity(BigDecimal.valueOf(10));
        createTradeDto.setPrice(BigDecimal.valueOf(100));

        doNothing().when(walletCryptoClient).updateWalletAndCheckFounds(createTradeDto);

        Trade createdTrade = new Trade();
        when(tradeDao.save(any(Trade.class))).thenReturn(createdTrade);
        // when
        Trade result = service.validateAndCreateTrade(createTradeDto);
        // then
        assertEquals(createdTrade, result);
    }
}
