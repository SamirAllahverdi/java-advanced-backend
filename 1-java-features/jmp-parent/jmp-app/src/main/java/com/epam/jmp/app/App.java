package com.epam.jmp.app;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.exception.SubscriptionNotFoundException;
import com.epam.jmp.bank.impl.BankImpl;
import com.epam.jmp.service.Bank;
import com.epam.jmp.service.service.impl.ServiceImpl;
import com.epam.jmp.service.service.Service;

import java.time.LocalDate;
import java.time.Month;

public class App {

    public static void main(String[] args) {
        Bank bank = new BankImpl();
        User userDwayne = new User("Dwayne", "Johnson", LocalDate.of(1972, Month.MAY, 2));
        BankCard creditBankCard = bank.createBankCard(userDwayne, BankCardType.CREDIT);

        User userTom = new User("Tom", "Cruise", LocalDate.of(1962, Month.JULY, 3));
        BankCard debitBankCard = bank.createBankCard(userTom, BankCardType.DEBIT);

        System.out.println("Logging bank cards..");
        System.out.println(creditBankCard);
        System.out.println(debitBankCard);

        Service service = new ServiceImpl();
        service.subscribe(creditBankCard);
        service.subscribe(debitBankCard);

        System.out.println("Logging subscriptions..");
        Subscription subCredit = service.getSubscriptionByBankCardNumber(creditBankCard.getNumber())
                .orElseThrow(() -> new SubscriptionNotFoundException(String.format("Not found subscription cardNumber = [%s]", creditBankCard.getNumber())));
        System.out.println(subCredit);

        Subscription subDebit = service.getSubscriptionByBankCardNumber(debitBankCard.getNumber())
                .orElseThrow(() -> new SubscriptionNotFoundException(String.format("Not found subscription cardNumber = [%s]", creditBankCard.getNumber())));
        System.out.println(subDebit);

        System.out.println("Logging all users..");
        service.getAllUsers().forEach(u -> System.out.println(u + ", isPayable = " + Service.isPayableUser(u)));

        System.out.println("Getting average user age");
        System.out.println(service.getAverageUsersAge());

        System.out.println("Getting all subscriptions by condition [bankCard = debitBankCard.getNumber()");
        System.out.println(service.getAllSubscriptionsByCondition(s -> s.getBankcard().equals(debitBankCard.getNumber())));
    }
}