package com.microservices.tradeservice.feignClient;

import com.microservices.tradeservice.dto.TradeDealDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "WALLET")
public interface WalletClient {

    @PutMapping(value = "v1/wallet/deposit")
    void updateUserWallet(TradeDealDto dto);
}
