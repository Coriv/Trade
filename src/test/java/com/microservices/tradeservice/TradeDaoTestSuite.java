package com.microservices.tradeservice;

import com.microservices.tradeservice.entity.Trade;
import com.microservices.tradeservice.repository.TradeDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeDaoTestSuite {

    @Autowired
    private TradeDao tradeDao;

    @Test
    void saveNewTradeAndFindByIdTest() {
        //given
        Trade trade = new Trade();
        trade.setUserId(1L);
        trade.setOpenDate(LocalDateTime.now());
        trade.setOpen(true);
        trade.setPrice(BigDecimal.valueOf(3.00));
        trade.setQuantity(BigDecimal.valueOf(100));
        trade.setValue(trade.getQuantity().multiply(trade.getPrice()));
        trade.setCryptoSymbol("BTC");
        //when
        tradeDao.save(trade);
        var savedTrade = tradeDao.findById(trade.getId())
                .orElseThrow();
        //then
        assertEquals(trade.getId(), savedTrade.getId());
        assertEquals(trade.getUserId(), savedTrade.getUserId());
        assertTrue(trade.getPrice().compareTo(savedTrade.getPrice()) == 0);
        assertTrue(trade.getQuantity().compareTo(savedTrade.getQuantity()) == 0);
        assertTrue(trade.getValue().compareTo(savedTrade.getValue()) == 0);
        assertEquals(trade.getOpenDate(), savedTrade.getOpenDate());
        assertEquals(trade.isOpen(), savedTrade.isOpen());
        //cleanUp
        tradeDao.deleteById(savedTrade.getId());
    }
}
