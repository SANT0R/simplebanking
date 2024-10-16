package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        Account account1 = new Account(1L, "1234567890", "John Doe", 5000.0);
        Account account2 = new Account(2L, "9876543210", "Jane Doe", 3000.0);

        accountRepository.save(account1);
        accountRepository.save(account2);
    }

    @Test
    public void testFindByAccountNumber() {
        // Act
        Account foundAccount = accountRepository.findByAccountNumber("1234567890");

        // Assert
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getOwner()).isEqualTo("John Doe");
    }

    @Test
    public void testFindAllByOwner() {
        // Act
        List<Account> accounts = accountRepository.findAllByOwner("Jane Doe");

        // Assert
        assertThat(accounts).isNotEmpty();
        assertThat(accounts.get(0).getOwner()).isEqualTo("Jane Doe");
    }

    @Test
    public void testFindAllByBalanceBetween() {
        // Act
        List<Account> accounts = accountRepository.findAllByBalanceBetween(2000.0, 6000.0);

        // Assert
        assertThat(accounts).isNotEmpty();
        assertThat(accounts.size()).isEqualTo(2);  // We expect 2 accounts in this range
    }
}
