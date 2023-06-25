package com.microservices.tradeservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UrlConfig {

    @Value("${wallet.deposit.money.url}")
    private String walletDepositUrl;
    @Value("${wallet.crypto.deposit.url}")
    private String walletCryptoDepositUrl;
}
