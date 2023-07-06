package com.microservices.tradeservice;

import com.microservices.tradeservice.dto.TradeDto;
import com.microservices.tradeservice.entity.Trade;
import com.microservices.tradeservice.mapper.TradeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TradeMapperTestSuite {
    @Autowired
    private TradeMapper mapper;

    @Test
    public void testMapToTradeDto() {
        // given
        Trade trade = new Trade();
        trade.setId(1L);
        trade.setQuantity(BigDecimal.valueOf(100));
        trade.setPrice(BigDecimal.valueOf(3.00));
        trade.setOpen(true);
        trade.setOpenDate(LocalDateTime.now());
        trade.setCloseDate(LocalDateTime.now().plusHours(1));
        trade.setCryptoSymbol("BTC");
        trade.setUserId(123L);
        // when
        TradeDto tradeDto = mapper.mapToTradeDto(trade);
        // then
        assertEquals(1L, tradeDto.getId());
        assertEquals(BigDecimal.valueOf(100), tradeDto.getQuantity());
        assertEquals(BigDecimal.valueOf(3.00), tradeDto.getPrice());
        assertEquals(BigDecimal.valueOf(300.00), tradeDto.getValue());
        assertTrue(tradeDto.isOpen());
        assertNotNull(tradeDto.getOpenDate());
        assertNotNull(tradeDto.getCloseDate());
        assertEquals("BTC", tradeDto.getCryptoSymbol());
        assertEquals(123L, tradeDto.getUserId());
    }

    @Test
    public void testMapToTradeDtoList() {
        // given
        List<Trade> trades = new ArrayList<>();
        Trade trade1 = new Trade();
        trade1.setId(1L);
        trade1.setQuantity(BigDecimal.valueOf(100));
        trade1.setPrice(BigDecimal.valueOf(3.00));
        trade1.setOpen(true);
        trade1.setOpenDate(LocalDateTime.now());
        trade1.setCloseDate(LocalDateTime.now().plusHours(1));
        trade1.setCryptoSymbol("BTC");
        trade1.setUserId(123L);
        trades.add(trade1);

        Trade trade2 = new Trade();
        trade2.setId(2L);
        trade2.setQuantity(BigDecimal.valueOf(200));
        trade2.setPrice(BigDecimal.valueOf(2.50));
        trade2.setOpen(false);
        trade2.setOpenDate(LocalDateTime.now().minusDays(1));
        trade2.setCloseDate(LocalDateTime.now().minusHours(1));
        trade2.setCryptoSymbol("ETH");
        trade2.setUserId(456L);
        trades.add(trade2);
        // when
        List<TradeDto> tradeDtoList = mapper.mapToTradeDtoList(trades);

        // then
        assertEquals(2, tradeDtoList.size());

        TradeDto tradeDto1 = tradeDtoList.get(0);
        assertEquals(1L, tradeDto1.getId());
        assertEquals(BigDecimal.valueOf(100), tradeDto1.getQuantity());
        assertEquals(BigDecimal.valueOf(3.00), tradeDto1.getPrice());
        assertEquals(BigDecimal.valueOf(300.00), tradeDto1.getValue());
        assertTrue(tradeDto1.isOpen());
        assertNotNull(tradeDto1.getOpenDate());
        assertNotNull(tradeDto1.getCloseDate());
        assertEquals("BTC", tradeDto1.getCryptoSymbol());
        assertEquals(123L, tradeDto1.getUserId());

        TradeDto tradeDto2 = tradeDtoList.get(1);
        assertEquals(2L, tradeDto2.getId());
        assertEquals(BigDecimal.valueOf(200), tradeDto2.getQuantity());
        assertEquals(BigDecimal.valueOf(2.50), tradeDto2.getPrice());
        assertEquals(BigDecimal.valueOf(500.00), tradeDto2.getValue());
        assertFalse(tradeDto2.isOpen());
        assertNotNull(tradeDto2.getOpenDate());
        assertNotNull(tradeDto2.getCloseDate());
        assertEquals("ETH", tradeDto2.getCryptoSymbol());
        assertEquals(456L, tradeDto2.getUserId());
    }

}
