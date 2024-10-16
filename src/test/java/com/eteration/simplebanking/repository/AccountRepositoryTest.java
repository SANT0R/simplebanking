package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account1;
    private Account account2;

    @BeforeEach
    public void setUp() {
        // Example account initialization
        account1 = new Account(null, "123456", "John Doe", 1000.0);
        account2 = new Account(null, "654321", "Jane Smith", 1500.0);

        accountRepository.save(account1);
        accountRepository.save(account2);
    }

    @Test
    public void testFindByAccountNumber() {
        Account foundAccount = accountRepository.findByAccountNumber("123456");
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getOwner()).isEqualTo("John Doe");
        assertThat(foundAccount.getBalance()).isEqualTo(1000.0);
    }

    @Test
    public void testFindAllByOwner() {
        List<Account> foundAccounts = accountRepository.findAllByOwner("John Doe");
        assertThat(foundAccounts).isNotEmpty();
        assertThat(foundAccounts.size()).isEqualTo(1);
        assertThat(foundAccounts.get(0).getAccountNumber()).isEqualTo("123456");
    }

    @Test
    public void testFindAllByBalanceBetween() {
        List<Account> foundAccounts = accountRepository.findAllByBalanceBetween(900.0, 1600.0);
        assertThat(foundAccounts).isNotEmpty();
        assertThat(foundAccounts.size()).isEqualTo(2);  // Both accounts should be in this range
        assertThat(foundAccounts.get(0).getBalance()).isBetween(900.0, 1600.0);
    }

    @Test
    public void testSaveAndRetrieveAccount() {
        Account account = new Account(null, "111222", "Mark Smith", 2000.0);
        Account savedAccount = accountRepository.save(account);
        assertThat(savedAccount.getId()).isNotNull();

        Account retrievedAccount = accountRepository.findById(savedAccount.getId()).orElse(null);
        assertThat(retrievedAccount).isNotNull();
        assertThat(retrievedAccount.getAccountNumber()).isEqualTo("111222");
        assertThat(retrievedAccount.getOwner()).isEqualTo("Mark Smith");
        assertThat(retrievedAccount.getBalance()).isEqualTo(2000.0);
    }
}

