package com.epam.jmp.bank.impl;


import com.epam.jmp.dto.*;
import com.epam.jmp.service.Bank;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class BankImpl implements Bank {

    private static final long LOWER_BOUND = 1111_1111_1111_1111L;
    private static final long UPPER_BOUND = 9999_9999_9999_9999L;
    private static final String algorithm = "Xoshiro256PlusPlus";
    private final RandomGenerator cardNumberGenerator;
    private final Map<BankCardType, BiFunction<String, User, BankCard>> cardFactories;

    public BankImpl() {
        this.cardNumberGenerator = RandomGeneratorFactory.of(algorithm).create(999);
        this.cardFactories = Map.of(
                BankCardType.DEBIT, DebitBankCard::new,
                BankCardType.CREDIT, CreditBankCard::new
        );
    }

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        validate(user, cardType);
        var cardNumber = String.valueOf(cardNumberGenerator.nextLong(LOWER_BOUND, UPPER_BOUND));
        return cardFactories.get(cardType).apply(cardNumber, user);
    }

    private void validate(User user, BankCardType cardType) {
        if (user == null)
            throw new IllegalArgumentException("Invalid user [null]");

        if (cardType == null)
            throw new IllegalArgumentException("Invalid card type [null]");
    }
}
