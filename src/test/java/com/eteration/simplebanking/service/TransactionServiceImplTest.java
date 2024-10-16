package com.eteration.simplebanking.service;

import com.eteration.simplebanking.dto.AccountDto;
import com.eteration.simplebanking.dto.DepositTransactionDto;
import com.eteration.simplebanking.dto.WithdrawalTransactionDto;
import com.eteration.simplebanking.dto.TransactionHistoryResponseDto;
import com.eteration.simplebanking.exception.ApiRequestException;
import com.eteration.simplebanking.mapper.DepositTransactionMapper;
import com.eteration.simplebanking.mapper.WithdrawalTransactionMapper;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.DepositTransactionRepository;
import com.eteration.simplebanking.repository.WithdrawalTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {

    @InjectMocks
    private TransactionService transactionService;

    @MockBean
    private DepositTransactionRepository depositRepository;

    @MockBean
    private WithdrawalTransactionRepository withdrawalRepository;

    @MockBean
    private AccountService accountService;

    @MockBean
    private DepositTransactionMapper depositMapper;

    @MockBean
    private WithdrawalTransactionMapper withdrawalMapper;

    private AccountDto accountDto;
    private DepositTransactionDto depositTransactionDto;
    private WithdrawalTransactionDto withdrawalTransactionDto;
    private DepositTransaction depositTransaction;
    private WithdrawalTransaction withdrawalTransaction;



    @Test
    public void testDoTransaction_Deposit() {
        when(accountService.getByAccountNumber("123456")).thenReturn(accountDto);
        when(depositMapper.dtoToEntity(depositTransactionDto)).thenReturn(depositTransaction);

        transactionService.doTransaction(depositTransactionDto);

        verify(depositRepository, times(1)).save(depositTransaction);
        verify(accountService, times(1)).update(accountDto);
        assertThat(accountDto.getBalance()).isEqualTo(1200.0);  // Balance after deposit
    }

    @Test
    public void testDoTransaction_Withdrawal_Success() {
        when(accountService.getByAccountNumber("123456")).thenReturn(accountDto);
        when(withdrawalMapper.dtoToEntity(withdrawalTransactionDto)).thenReturn(withdrawalTransaction);

        transactionService.doTransaction(withdrawalTransactionDto);

        verify(withdrawalRepository, times(1)).save(withdrawalTransaction);
        verify(accountService, times(1)).update(accountDto);
        assertThat(accountDto.getBalance()).isEqualTo(900.0);  // Balance after withdrawal
    }

    @Test
    public void testDoTransaction_Withdrawal_InsufficientBalance() {
        accountDto.setBalance(50.0);  // Setting a lower balance than the withdrawal amount
        when(accountService.getByAccountNumber("123456")).thenReturn(accountDto);

        assertThatThrownBy(() -> transactionService.doTransaction(withdrawalTransactionDto))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("Account balance is not enough");

        verify(withdrawalRepository, never()).save(any(WithdrawalTransaction.class));
        verify(accountService, never()).update(any(AccountDto.class));
    }

    @Test
    public void testGetTransactionHistory() {
        when(accountService.getByAccountNumber("123456")).thenReturn(accountDto);
        when(depositRepository.findAllByAccount_AccountNumber("123456")).thenReturn(Arrays.asList(depositTransaction));
        when(withdrawalRepository.findAllByAccount_AccountNumber("123456")).thenReturn(Arrays.asList(withdrawalTransaction));

        List<TransactionHistoryResponseDto> result = transactionService.getTransactionHistory("123456");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);  // One deposit and one withdrawal
    }
}


