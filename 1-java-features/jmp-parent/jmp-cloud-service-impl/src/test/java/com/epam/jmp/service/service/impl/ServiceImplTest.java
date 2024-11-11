package com.epam.jmp.service.service.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.service.Service;
import com.epam.jmp.service.exception.DuplicateEntryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceImplTest {
    private final User TEST_ADULT_USER = new User("Donald", "Trump", LocalDate.of(1946, Month.JUNE, 14));
    private final User TEST_MINOR_USER = new User("Iain", "Armitage", LocalDate.of(2008, Month.JULY, 15));

    private final BankCard TEST_BANK_CARD = new BankCard("testCardNumber", TEST_ADULT_USER);
    private final BankCard TEST_BANK_CARD_2 = new BankCard("testCardNumber2", TEST_MINOR_USER);

    private Service service;

    @BeforeEach
    public void setUp() {
        service = new ServiceImpl();
        service.subscribe(TEST_BANK_CARD);
        service.subscribe(TEST_BANK_CARD_2);
    }

    @Test
    public void shouldThrowExceptionIfBankCardDuplicated() {
        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class,
                () -> service.subscribe(TEST_BANK_CARD));
        assertEquals(String.format("User's bank card is already subscribed [%s]", TEST_BANK_CARD.getUser().getFullName()), exception.getMessage());
    }

    @Test
    public void shouldBankCardSubscribe() {
        service.getSubscriptionByBankCardNumber(TEST_BANK_CARD.getNumber())
                .ifPresentOrElse(s -> {
                    Assertions.assertEquals(s.getBankcard(), TEST_BANK_CARD.getNumber());
                    Assertions.assertEquals(s.getStartDate(), LocalDate.now());
                }, () -> fail("Bank card is not subscribed properly"));
    }

    @Test
    public void shouldThrowExceptionIfSubscriptionNotFoundByCardNumber() {
        DuplicateEntryException exception = assertThrows(DuplicateEntryException.class,
                () -> service.subscribe(TEST_BANK_CARD));
        assertEquals(String.format("User's bank card is already subscribed [%s]", TEST_BANK_CARD.getUser().getFullName()), exception.getMessage());
    }

    @Test
    public void shouldGetAllUsers() {
        List<User> users = service.getAllUsers();

        assertTrue(users != null && users.size() == 2);
        User user = users.get(0);
        assertEquals(TEST_ADULT_USER.getSurname(), user.getSurname());
        assertEquals(TEST_ADULT_USER.getName(), user.getName());
        assertEquals(TEST_ADULT_USER.getBirthday(), user.getBirthday());

        User user2 = users.get(1);
        assertEquals(TEST_MINOR_USER.getSurname(), user2.getSurname());
        assertEquals(TEST_MINOR_USER.getName(), user2.getName());
        assertEquals(TEST_MINOR_USER.getBirthday(), user2.getBirthday());
    }

    @Test
    public void shouldCalculateAverageUsersAge() {
        assertEquals(47.0, service.getAverageUsersAge());
    }

    @Test
    public void shouldDefinePayableUser() {
        assertAll(
                () -> assertFalse(Service.isPayableUser(TEST_MINOR_USER), "Minor user should not be payable"),
                () -> assertTrue(Service.isPayableUser(TEST_ADULT_USER), "Adult user should be payable")
        );
    }

    @Test
    public void shouldGetAllSubscriptionsByCondition() {
        List<Subscription> subscriptions = service.getAllSubscriptionsByCondition(s -> s.getBankcard().equals(TEST_BANK_CARD.getNumber()));

        assertTrue(subscriptions != null && subscriptions.size() == 1);
        Subscription subscription = subscriptions.get(0);
        assertEquals(TEST_BANK_CARD.getNumber(), subscription.getBankcard());
        assertEquals(LocalDate.now(), subscription.getStartDate());
    }
}
