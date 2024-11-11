package com.epam.jmp.service.service.impl;

import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.exception.DuplicateEntryException;
import com.epam.jmp.service.service.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ServiceImpl implements Service {

    public final Map<Subscription, User> subscriptionUserMap;

    public ServiceImpl() {
        this.subscriptionUserMap = new HashMap<>();
    }

    @Override
    public void subscribe(BankCard bankCard) {
        boolean duplicate = subscriptionUserMap.keySet().stream().anyMatch(s -> s.getBankcard().equals(bankCard.getNumber()));
        if (duplicate)
            throw new DuplicateEntryException(String.format("User's bank card is already subscribed [%s]", bankCard.getUser().getFullName()));
        subscriptionUserMap.put(new Subscription(bankCard.getNumber(), LocalDate.now()), bankCard.getUser());
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return subscriptionUserMap.keySet().stream().filter(s -> s.getBankcard().equals(cardNumber)).findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return subscriptionUserMap.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return subscriptionUserMap.keySet().stream().filter(condition).collect(Collectors.toUnmodifiableList());
    }
}
