package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WithdrawalTransactionRepositoryTest {

    @Autowired
    private WithdrawalTransactionRepository withdrawalTransactionRepository;

    private WithdrawalTransaction withdrawalTransaction1;
    private WithdrawalTransaction withdrawalTransaction2;

    @BeforeEach
    public void setUp() {
        // Örnek hesap
        Account account = new Account(null, "123456", "John Doe", 1000.0);

        // Create WithdrawalTransaction examples
        withdrawalTransaction1 = new WithdrawalTransaction();
        withdrawalTransaction1.setAccount(account);
        withdrawalTransaction1.setAmount(150.0);
        withdrawalTransaction1.setDate(LocalDateTime.now().minusDays(4).toEpochSecond(ZoneOffset.UTC));  // 4 gün önce

        withdrawalTransaction2 = new WithdrawalTransaction();
        withdrawalTransaction2.setAccount(account);
        withdrawalTransaction2.setAmount(300.0);
        withdrawalTransaction2.setDate(LocalDateTime.now().minusDays(2).toEpochSecond(ZoneOffset.UTC));  // 2 gün önce

        // Veritabanına kayıt
        withdrawalTransactionRepository.save(withdrawalTransaction1);
        withdrawalTransactionRepository.save(withdrawalTransaction2);
    }

    @Test
    public void testFindAllByAccount_AccountNumber() {
        List<WithdrawalTransaction> transactions = withdrawalTransactionRepository.findAllByAccount_AccountNumber("123456");
        assertThat(transactions).isNotEmpty();
        assertThat(transactions.size()).isEqualTo(2);
        assertThat(transactions.get(0).getAccount().getAccountNumber()).isEqualTo("123456");
    }

    @Test
    public void testFindAllByDateBetween() {
        Long startDate = LocalDateTime.now().minusDays(5).toEpochSecond(ZoneOffset.UTC);  // 5 gün önce
        Long endDate = LocalDateTime.now().minusDays(1).toEpochSecond(ZoneOffset.UTC);    // 1 gün önce

        List<WithdrawalTransaction> transactions = withdrawalTransactionRepository.findAllByDateBetween(startDate, endDate);
        assertThat(transactions).isNotEmpty();
        assertThat(transactions.size()).isEqualTo(2);  // İki işlem de bu aralığa dahil
    }

    @Test
    public void testFindAllByAmountBetween() {
        List<WithdrawalTransaction> transactions = withdrawalTransactionRepository.findAllByAmountBetween(100.0, 200.0);
        assertThat(transactions).isNotEmpty();
        assertThat(transactions.size()).isEqualTo(1);  // Sadece withdrawalTransaction1 miktarı 150.0 bu aralıkta
        assertThat(transactions.get(0).getAmount()).isEqualTo(150.0);
    }

    @Test
    public void testSaveAndRetrieveWithdrawalTransaction() {
        WithdrawalTransaction newTransaction = new WithdrawalTransaction();
        Account account = new Account(null, "654321", "Jane Smith", 1500.0);
        newTransaction.setAccount(account);
        newTransaction.setAmount(500.0);
        newTransaction.setDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        WithdrawalTransaction savedTransaction = withdrawalTransactionRepository.save(newTransaction);
        assertThat(savedTransaction.getId()).isNotNull();
        assertThat(savedTransaction.getAmount()).isEqualTo(500.0);

        WithdrawalTransaction retrievedTransaction = withdrawalTransactionRepository.findById(savedTransaction.getId()).orElse(null);
        assertThat(retrievedTransaction).isNotNull();
        assertThat(retrievedTransaction.getAmount()).isEqualTo(500.0);
        assertThat(retrievedTransaction.getAccount().getAccountNumber()).isEqualTo("654321");
    }
}

