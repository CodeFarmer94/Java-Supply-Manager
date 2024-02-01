package com.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.entities.Balance;

import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private BalanceService balanceService;

  
    @Test
    void testListenPaymentEvent() {
        double initialBalance = 100.0;
        balanceService.createBalance(initialBalance, "Initial Balance");

        double paymentAmount = 50.0;
        balanceService.listenPaymentEvent(paymentAmount);

        Balance lastBalance = balanceService.findLastBalance();
        assertEquals(initialBalance - paymentAmount, lastBalance.getBalanceAmount());
    }

    @Test
    void testListenGetPaymentEvent() {
        double initialBalance = 100.0;
        balanceService.createBalance(initialBalance, "Initial Balance");

        double profitAmount = 30.0;
        balanceService.listenGetPaymentEvent(profitAmount);

        Balance lastBalance = balanceService.findLastBalance();
        assertEquals(initialBalance + profitAmount, lastBalance.getBalanceAmount());
    }

    @Test
    void testFindLastBalanceNoExistingBalance() {
        Balance lastBalance = balanceService.findLastBalance();
        assertNull(lastBalance);
    }

    @Test
    void testCreateBalance() {
        double balanceAmount = 200.0;
        String transactionType = "Test Transaction";
        balanceService.createBalance(balanceAmount, transactionType);

        Balance lastBalance = balanceService.findLastBalance();
        assertNotNull(lastBalance);
        assertEquals(balanceAmount, lastBalance.getBalanceAmount());
        assertEquals(transactionType, lastBalance.getTransactionType());
    }

    @Test
    void testAddFunds() {
        double initialBalance = 50.0;
        balanceService.createBalance(initialBalance, "Initial Balance");

        double fundsToAdd = 30.0;
        balanceService.addFunds(fundsToAdd);

        Balance lastBalance = balanceService.findLastBalance();
        assertEquals(initialBalance + fundsToAdd, lastBalance.getBalanceAmount());
    }

    @Test
    void testRemoveFunds() {
        double initialBalance = 80.0;
        balanceService.createBalance(initialBalance, "Initial Balance");

        double fundsToRemove = 20.0;
        balanceService.removeFunds(fundsToRemove);

        Balance lastBalance = balanceService.findLastBalance();
        assertEquals(initialBalance - fundsToRemove, lastBalance.getBalanceAmount());
    }

   

}
