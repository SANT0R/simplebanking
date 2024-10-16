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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    private Account account;
    private AccountDto accountDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account(1L, "123456", "John Doe", 1000.0);
        accountDto = new AccountDto(1L, "123456", "John Doe", 1000.0);
    }

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
    public void testDeleteAllByOwner_AccountNotFound() {
        // Mock davranışı: Hiç hesap bulunamıyor
        when(accountRepository.findAllByOwner("Unknown Owner")).thenReturn(new ArrayList<>());

        // Account bulunamadığında exception fırlatılması bekleniyor
        Exception exception = assertThrows(ApiRequestException.class, () -> {
            accountService.deleteAllByOwner("Unknown Owner");
        });

        // Exception mesajı doğrulanıyor
        assertEquals(ApiRequestException.class, exception.getClass());

        // deleteAll metodunun hiç çağrılmadığından emin olunuyor
        verify(accountRepository, never()).deleteAll(anyList());
    }

    @Test
    public void testDeleteAll() {
        accountService.deleteAll();

        verify(accountRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteById() {
        // Mock davranışları ayarlanıyor
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.getById(1L)).thenReturn(account);

        // deleteById metodunu çağırıyoruz
        accountService.deleteById(1L);

        // Doğru metodlar çağrıldı mı kontrol ediliyor
        verify(accountRepository).findById(1L);
        verify(accountRepository).getById(1L);
        verify(accountRepository).delete(account);
    }
    @Test
    public void testDeleteById_AccountNotFound() {
        // Mock davranışı: Account bulunamıyor
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Account bulunamadığında exception fırlatılması bekleniyor
        Exception exception = assertThrows(ApiRequestException.class, () -> {
            accountService.deleteById(1L);
        });

        // Exception mesajı doğrulanıyor
        assertEquals("Account not found", exception.getMessage());

        // delete metodunun çağrılmadığından emin olunuyor
        verify(accountRepository, never()).delete(any(Account.class));
    }
}
