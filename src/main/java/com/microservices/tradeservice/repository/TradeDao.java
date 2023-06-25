package com.microservices.tradeservice.repository;

import com.microservices.tradeservice.entity.Trade;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TradeDao extends CrudRepository<Trade, Long> {
    List<Trade> findAllByOpenIsTrue();
    List<Trade> findAllByUserId(Long userId);
}
