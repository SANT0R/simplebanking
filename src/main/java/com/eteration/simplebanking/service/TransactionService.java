package com.eteration.simplebanking.service;

import com.eteration.simplebanking.dto.DepositTransactionDto;
import com.eteration.simplebanking.dto.TransactionHistoryResponseDto;
import com.eteration.simplebanking.dto.WithdrawalTransactionDto;

import java.util.List;

public interface TransactionService {
    /**
     * Make Deposit Transaction
     * @param transactionDto -
     */
    void doTransaction(DepositTransactionDto transactionDto);

    /**
     * Make Withdrawal Transaction
     * @param transactionDto -
     */
    void doTransaction(WithdrawalTransactionDto transactionDto);

    /**
     * get Transaction History by account number
     * @param accountNumber -
     * @return TransactionHistoryResponseDto
     */
    List<TransactionHistoryResponseDto> getTransactionHistory(String accountNumber);
}
