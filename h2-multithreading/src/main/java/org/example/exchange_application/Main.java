package org.example.exchange_application;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.*;

import org.example.exchange_application.dao.ExchangeDAO;
import org.example.exchange_application.model.Currency;
import org.example.exchange_application.model.CurrencyEnum;
import org.example.exchange_application.model.User;
import org.example.exchange_application.service.ExchangeService;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        ExchangeService service = new ExchangeService(new ExchangeDAO());

        Currency eur = new Currency(CurrencyEnum.EUR, BigDecimal.valueOf(100));
        Currency usd = new Currency(CurrencyEnum.USD, BigDecimal.valueOf(100));
        User user = new User("Alex", Arrays.asList(eur, usd));
        service.createSampleData(user);

        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<?> future1 = es.submit(() -> service.exchange(BigDecimal.valueOf(50), CurrencyEnum.EUR, CurrencyEnum.USD));
        Future<?> future2 = es.submit(() -> service.exchange(BigDecimal.valueOf(20), CurrencyEnum.USD, CurrencyEnum.EUR));

        future1.get(10, TimeUnit.SECONDS);
        future2.get(10, TimeUnit.SECONDS);
    }
}
