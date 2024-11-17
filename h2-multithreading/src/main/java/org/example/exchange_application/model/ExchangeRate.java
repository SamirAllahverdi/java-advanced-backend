package org.example.exchange_application.model;

import java.math.BigDecimal;
import java.util.Arrays;

public enum ExchangeRate {

    FROM_USD_TO_EUR(CurrencyEnum.USD, CurrencyEnum.EUR, BigDecimal.valueOf(0.92)),
    FROM_EUR_TO_USD(CurrencyEnum.EUR, CurrencyEnum.USD, BigDecimal.valueOf(1.09));

    private final CurrencyEnum source;
    private final CurrencyEnum target;
    private final BigDecimal rate;

    ExchangeRate(CurrencyEnum source, CurrencyEnum target, BigDecimal rate) {
        this.source = source;
        this.target = target;
        this.rate = rate;
    }

    public static BigDecimal getExchangeRate(CurrencyEnum source, CurrencyEnum target) {
        return Arrays.stream(ExchangeRate.values())
                .filter(er -> er.source == source && er.target == target)
                .map(ExchangeRate::getRate)
                .findFirst()
                .orElseThrow();

    }


    public BigDecimal getRate() {
        return rate;
    }
}
