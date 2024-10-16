package com.eteration.simplebanking.service;

import com.eteration.simplebanking.dto.AccountDto;
import com.eteration.simplebanking.exception.ApiRequestException;
import com.eteration.simplebanking.mapper.AccountMapper;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    @InjectMocks
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountMapper accountMapper;

    private Account account;
    private AccountDto accountDto;


    @Test
    public void testAdd() {
        when(accountMapper.dtoToEntity(accountDto)).thenReturn(account);

        accountService.add(accountDto);

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testUpdate() {
        when(accountRepository.findById(accountDto.getId())).thenReturn(Optional.of(account));
        when(accountMapper.dtoToEntity(accountDto)).thenReturn(account);

        accountService.update(accountDto);

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testGetAll() {
        List<Account> accounts = Arrays.asList(account);
        when(accountRepository.findAll()).thenReturn(accounts);
        when(accountMapper.entityToDtoList(accounts)).thenReturn(Arrays.asList(accountDto));

        List<AccountDto> result = accountService.getAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAccountNumber()).isEqualTo("123456");
    }

    @Test
    public void testGetById_ExistingId() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountMapper.entityToDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getAccountNumber()).isEqualTo("123456");
    }

    @Test
    public void testGetById_NonExistingId() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.getById(1L))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("Account not found");
    }

    @Test
    public void testGetByAccountNumber() {
        when(accountRepository.findByAccountNumber("123456")).thenReturn(account);
        when(accountMapper.entityToDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.getByAccountNumber("123456");

        assertThat(result).isNotNull();
        assertThat(result.getAccountNumber()).isEqualTo("123456");
    }

    @Test
    public void testGetByOwner() {
        List<Account> accounts = Arrays.asList(account);
        when(accountRepository.findAllByOwner("John Doe")).thenReturn(accounts);
        when(accountMapper.entityToDtoList(accounts)).thenReturn(Arrays.asList(accountDto));

        List<AccountDto> result = accountService.getByOwner("John Doe");

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    public void testGetByBalance() {
        List<Account> accounts = Arrays.asList(account);
        when(accountRepository.findAllByBalanceBetween(500.0, 1500.0)).thenReturn(accounts);
        when(accountMapper.entityToDtoList(accounts)).thenReturn(Arrays.asList(accountDto));

        List<AccountDto> result = accountService.getByBalance(500.0, 1500.0);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    public void testDeleteAllByOwner_OwnerExists() {
        List<Account> accounts = Arrays.asList(account);
        when(accountRepository.findAllByOwner("John Doe")).thenReturn(accounts);

        accountService.deleteAllByOwner("John Doe");

        verify(accountRepository, times(1)).deleteAll(accounts);
    }

    @Test
    public void testDeleteAllByOwner_OwnerNotFound() {
        when(accountRepository.findAllByOwner("John Doe")).thenReturn(Arrays.asList());

        assertThatThrownBy(() -> accountService.deleteAllByOwner("John Doe"))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("could not be found");

        verify(accountRepository, never()).deleteAll(anyList());
    }

    @Test
    public void testDeleteAll() {
        accountService.deleteAll();

        verify(accountRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteById() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.deleteById(1L);

        verify(accountRepository, times(1)).delete(account);
    }
}

