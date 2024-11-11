package com.epam.jmp.bank.impl;

import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.dto.User;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.service.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankImplTest {

    private final User TEST_USER = new User("Donald", "Trump", LocalDate.of(1946, Month.JUNE, 14));
    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new BankImpl();
    }

    @ParameterizedTest
    @EnumSource(value = BankCardType.class, names = {"DEBIT", "CREDIT"})
    public void shouldCreateBankCard(BankCardType cardType) {
        var bc = bank.createBankCard(TEST_USER, cardType);
        assertNotNull(bc);
        assertTrue(bc.getNumber() != null && bc.getNumber().length() == 16);
        assertEquals(TEST_USER, bc.getUser());

        if (bc instanceof DebitBankCard dbc) {
            assertEquals(0, dbc.getBalance());
        } else if (bc instanceof CreditBankCard cbc) {
            assertEquals(0, cbc.getCreditLimit());
        } else {
            fail(String.format("Not expected sub-class of BankCard [%s]", bc.getClass()));
        }
    }

    @Test
    public void shouldThrowExceptionIfCardTypeNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bank.createBankCard(null, BankCardType.CREDIT));
        assertEquals("Invalid user [null]", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfUserNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bank.createBankCard(TEST_USER, null));
        assertEquals("Invalid card type [null]", exception.getMessage());
    }
}
