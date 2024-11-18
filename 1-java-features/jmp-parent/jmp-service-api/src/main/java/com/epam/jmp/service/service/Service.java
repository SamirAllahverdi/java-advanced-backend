package com.epam.jmp.service.service;

import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {

    int PAYABLE_USER_AGE = 18;

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);

    default double getAverageUsersAge() {
        return getAllUsers().stream()
                .mapToDouble(Service::getUserAge)
                .average()
                .orElseThrow(RuntimeException::new);
    }

    private static int getUserAge(User u) {
        return (int) u.getBirthday().until(LocalDate.now(), ChronoUnit.YEARS);
    }

    static boolean isPayableUser(User user) {
        int userAge = getUserAge(user);
        return userAge >= PAYABLE_USER_AGE;
    }
}
