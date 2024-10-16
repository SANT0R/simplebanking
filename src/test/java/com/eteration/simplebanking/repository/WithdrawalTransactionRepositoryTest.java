package com.eteration.simplebanking.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase
public class WithdrawalTransactionRepositoryTest {

    @Autowired
    private WithdrawalTransactionRepository withdrawalTransactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account(null, "123456", "John Doe", 5000.0);
        accountRepository.save(account);
        WithdrawalTransaction withdrawalTransaction1 = new WithdrawalTransaction(null, account, "123456", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), 1000.0);
        WithdrawalTransaction withdrawalTransaction2 = new WithdrawalTransaction(null, account, "123456", LocalDateTime.now().minusDays(1).toEpochSecond(ZoneOffset.UTC), 2000.0);

        withdrawalTransactionRepository.save(withdrawalTransaction1);
        withdrawalTransactionRepository.save(withdrawalTransaction2);
    }

    @Test
    @Transactional
    public void testFindAllByAccount_AccountNumber() {
        List<WithdrawalTransaction> transactions = withdrawalTransactionRepository.findAllByAccount_AccountNumber("123456");

        assertThat(transactions).hasSize(2);
        assertThat(transactions).extracting(WithdrawalTransaction::getAmount).contains(1000.0, 2000.0);
    }

    @Test
    @Transactional
    public void testFindAllByDateBetween() {
        Long startDate = LocalDateTime.now().minusDays(2).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        List<WithdrawalTransaction> transactions = withdrawalTransactionRepository.findAllByDateBetween(startDate, endDate);

        assertThat(transactions).hasSize(2);
    }

    @Test
    @Transactional
    public void testFindAllByAmountBetween() {
        List<WithdrawalTransaction> transactions = withdrawalTransactionRepository.findAllByAmountBetween(500.0, 1500.0);

        assertThat(transactions).hasSize(1); // 1000.0 için bir tane sonuç bekliyoruz
        assertEquals(1000.0, transactions.get(0).getAmount());
    }
}
