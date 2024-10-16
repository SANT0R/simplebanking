package com.eteration.simplebanking.repository;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DepositTransactionRepositoryTest {

    @Autowired
    private DepositTransactionRepository depositTransactionRepository;

    private DepositTransaction depositTransaction1;
    private DepositTransaction depositTransaction2;

    @BeforeEach
    public void setUp() {
        // Example account
        Account account = new Account(null, "123456", "John Doe", 1000.0);

        // Create DepositTransaction examples
        depositTransaction1 = new DepositTransaction();
        depositTransaction1.setAccount(account);
        depositTransaction1.setAmount(200.0);
        depositTransaction1.setDate(LocalDateTime.now().minusDays(5).toEpochSecond(ZoneOffset.UTC));  // 5 days ago

        depositTransaction2 = new DepositTransaction();
        depositTransaction2.setAccount(account);
        depositTransaction2.setAmount(500.0);
        depositTransaction2.setDate(LocalDateTime.now().minusDays(2).toEpochSecond(ZoneOffset.UTC));  // 2 days ago

        // Save the transactions to the repository
        depositTransactionRepository.save(depositTransaction1);
        depositTransactionRepository.save(depositTransaction2);
    }

    @Test
    public void testFindAllByAccount_AccountNumber() {
        List<DepositTransaction> transactions = depositTransactionRepository.findAllByAccount_AccountNumber("123456");
        assertThat(transactions).isNotEmpty();
        assertThat(transactions.size()).isEqualTo(2);
        assertThat(transactions.get(0).getAccount().getAccountNumber()).isEqualTo("123456");
    }

    @Test
    public void testFindAllByDateBetween() {
        Long startDate = LocalDateTime.now().minusDays(6).toEpochSecond(ZoneOffset.UTC);  // 6 days ago
        Long endDate = LocalDateTime.now().minusDays(1).toEpochSecond(ZoneOffset.UTC);  // 1 day ago

        List<DepositTransaction> transactions = depositTransactionRepository.findAllByDateBetween(startDate, endDate);
        assertThat(transactions).isNotEmpty();
        assertThat(transactions.size()).isEqualTo(2);  // Both transactions fall in this range
    }

    @Test
    public void testFindAllByAmountBetween() {
        List<DepositTransaction> transactions = depositTransactionRepository.findAllByAmountBetween(100.0, 300.0);
        assertThat(transactions).isNotEmpty();
        assertThat(transactions.size()).isEqualTo(1);  // Only depositTransaction1 has amount 200.0 in this range
        assertThat(transactions.get(0).getAmount()).isEqualTo(200.0);
    }

    @Test
    public void testSaveAndRetrieveDepositTransaction() {
        DepositTransaction newTransaction = new DepositTransaction();
        Account account = new Account(null, "654321", "Jane Smith", 1500.0);
        newTransaction.setAccount(account);
        newTransaction.setAmount(300.0);
        newTransaction.setDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        DepositTransaction savedTransaction = depositTransactionRepository.save(newTransaction);
        assertThat(savedTransaction.getId()).isNotNull();
        assertThat(savedTransaction.getAmount()).isEqualTo(300.0);

        DepositTransaction retrievedTransaction = depositTransactionRepository.findById(savedTransaction.getId()).orElse(null);
        assertThat(retrievedTransaction).isNotNull();
        assertThat(retrievedTransaction.getAmount()).isEqualTo(300.0);
        assertThat(retrievedTransaction.getAccount().getAccountNumber()).isEqualTo("654321");
    }
}

