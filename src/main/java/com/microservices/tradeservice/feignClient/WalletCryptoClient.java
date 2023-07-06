package com.microservices.tradeservice.feignClient;

import com.microservices.tradeservice.dto.CreateTradeDto;
import com.microservices.tradeservice.dto.TradeDealDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "WALLET-CRYPTO")
public interface WalletCryptoClient {

    @PutMapping(value = "v1/wallet-crypto/check-trade")
    void updateWalletAndCheckFounds(@RequestBody CreateTradeDto createTradeDto);

    @PutMapping(value = "v1/wallet-crypto/deposit")
    void updateUserCashWallet(TradeDealDto dto);
}
