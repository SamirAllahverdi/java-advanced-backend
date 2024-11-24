package org.example.exchange_application.service;


import org.example.exchange_application.dao.ExchangeDAO;
import org.example.exchange_application.model.Currency;
import org.example.exchange_application.model.CurrencyEnum;
import org.example.exchange_application.model.ExchangeRate;
import org.example.exchange_application.model.User;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class ExchangeService {
    private static final Logger logger = Logger.getLogger(ExchangeService.class.getName());
    private final ExchangeDAO dao;
    private final Lock lock = new ReentrantLock();

    public ExchangeService(ExchangeDAO dao) {
        this.dao = dao;
    }

    public void createSampleData(User user) {
        dao.createSampleData(user);
    }

    public void exchange(BigDecimal amount, CurrencyEnum source, CurrencyEnum target) {
        logger.info(Thread.currentThread().getName() + " starting..");
        try {
            lock.lock();
            logger.info(Thread.currentThread().getName() + " locked..");
            User user = dao.read();
            Currency currencySource = user.getCurrencies().stream().filter(c -> c.getName() == source).findFirst().orElseThrow();
            Currency currencyTarget = user.getCurrencies().stream().filter(c -> c.getName() == target).findFirst().orElseThrow();
            validate(amount, currencySource);
            logger.info(Thread.currentThread().getName() + " values before calc, " + source + " " + currencySource.getValue() + " -> " + target + " " + currencyTarget.getValue() + ", amount = " + amount);

            BigDecimal exchangeRate = ExchangeRate.getExchangeRate(currencySource.getName(), currencyTarget.getName());
            BigDecimal exchange = amount.multiply(exchangeRate);

            BigDecimal newSource = currencySource.getValue().subtract(amount);
            BigDecimal newTarget = currencyTarget.getValue().add(exchange);

            logger.info(Thread.currentThread().getName() + " values before calc, " + source + " " + newSource + " -> " + target + " " + newTarget + " with exchange rate " + exchangeRate);

            currencySource.setValue(newSource);
            currencyTarget.setValue(newTarget);

            dao.write(user);
        } finally {
            logger.info(Thread.currentThread().getName() + " unlocked");
            lock.unlock();
        }
    }

    private void validate(BigDecimal value, Currency currencySource) {
        if (currencySource.getValue().compareTo(value) < 0) {
            throw new IllegalArgumentException("Not enough money");
        }
    }
}
