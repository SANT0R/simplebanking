package com.eteration.simplebanking.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
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
public class DepositTransactionRepositoryTest {

    @Autowired
    private DepositTransactionRepository depositTransactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account(null, "123456", "John Doe", 5000.0);
        accountRepository.save(account);
        DepositTransaction depositTransaction1 = new DepositTransaction(null, account, "123456", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), 1000.0);
        DepositTransaction depositTransaction2 = new DepositTransaction(null, account, "123456", LocalDateTime.now().minusDays(1).toEpochSecond(ZoneOffset.UTC), 2000.0);

        depositTransactionRepository.save(depositTransaction1);
        depositTransactionRepository.save(depositTransaction2);
    }

    @Test
    @Transactional
    public void testFindAllByAccount_AccountNumber() {
        List<DepositTransaction> transactions = depositTransactionRepository.findAllByAccount_AccountNumber("123456");

        assertThat(transactions).hasSize(2);
        assertThat(transactions).extracting(DepositTransaction::getAmount).contains(1000.0, 2000.0);
    }

    @Test
    @Transactional
    public void testFindAllByDateBetween() {
        Long startDate = LocalDateTime.now().minusDays(2).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        List<DepositTransaction> transactions = depositTransactionRepository.findAllByDateBetween(startDate, endDate);

        assertThat(transactions).hasSize(2);
    }

    @Test
    @Transactional
    public void testFindAllByAmountBetween() {
        List<DepositTransaction> transactions = depositTransactionRepository.findAllByAmountBetween(500.0, 1500.0);

        assertThat(transactions).hasSize(1); // 1000.0 için bir tane sonuç bekliyoruz
        assertEquals(1000.0, transactions.get(0).getAmount());
    }
}
