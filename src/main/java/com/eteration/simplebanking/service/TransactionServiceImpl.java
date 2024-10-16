package com.eteration.simplebanking.service;


import com.eteration.simplebanking.dto.*;
import com.eteration.simplebanking.exception.ApiRequestException;
import com.eteration.simplebanking.mapper.DepositTransactionMapper;
import com.eteration.simplebanking.mapper.WithdrawalTransactionMapper;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.DepositTransactionRepository;
import com.eteration.simplebanking.repository.WithdrawalTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired private DepositTransactionRepository depositRepository;
    @Autowired private WithdrawalTransactionRepository withdrawalRepository;
    @Autowired private AccountService accountService;

    @Autowired private DepositTransactionMapper depositMapper;
    @Autowired private WithdrawalTransactionMapper withdrawalMapper;


    @Override
    public void doTransaction(DepositTransactionDto transactionDto) {

        AccountDto accountDto = accountService.getByAccountNumber(transactionDto.getAccountNumber());
        accountDto.setBalance(accountDto.getBalance() + transactionDto.getAmount());
        transactionDto.setAccount(accountDto);

        depositRepository.save(depositMapper.dtoToEntity(transactionDto));
        accountService.update(accountDto);
    }

    @Override
    public void doTransaction(WithdrawalTransactionDto transactionDto) {

        AccountDto accountDto = accountService.getByAccountNumber(transactionDto.getAccountNumber());
        if (accountDto.getBalance() < transactionDto.getAmount()) {
            throw new ApiRequestException(
                    "Account balance is not enough",
                    HttpStatus.METHOD_NOT_ALLOWED);
        }

        accountDto.setBalance(accountDto.getBalance() - transactionDto.getAmount());
        transactionDto.setAccount(accountDto);

        withdrawalRepository.save(withdrawalMapper.dtoToEntity(transactionDto));
        accountService.update(accountDto);
    }

    @Override
    public List<TransactionHistoryResponseDto> getTransactionHistory(String accountNumber) {

        AccountDto accountDto = accountService.getByAccountNumber(accountNumber);

        List<DepositTransaction> depositTransactionList = depositRepository.findAllByAccount_AccountNumber(accountDto.getAccountNumber());
        List<WithdrawalTransaction> withdrawalTransactionList = withdrawalRepository.findAllByAccount_AccountNumber(accountDto.getAccountNumber());
        List<TransactionHistoryResponseDto> transactionHistoryResponseDtoList = new ArrayList<>();

        depositTransactionList.forEach(depositTransaction -> {
            TransactionHistoryResponseDto transactionHistoryResponseDto = new TransactionHistoryResponseDto();
            transactionHistoryResponseDto.setId(depositTransaction.getId());
            transactionHistoryResponseDto.setAmount(depositTransaction.getAmount());
            transactionHistoryResponseDto.setDate(depositTransaction.getDate());
            transactionHistoryResponseDto.setTransactionType(depositTransaction.getTransactionType());

            transactionHistoryResponseDtoList.add(transactionHistoryResponseDto);
        });

        withdrawalTransactionList.forEach(withdrawalTransaction -> {
            TransactionHistoryResponseDto transactionHistoryResponseDto = new TransactionHistoryResponseDto();
            transactionHistoryResponseDto.setId(withdrawalTransaction.getId());
            transactionHistoryResponseDto.setAmount(withdrawalTransaction.getAmount());
            transactionHistoryResponseDto.setDate(withdrawalTransaction.getDate());
            transactionHistoryResponseDto.setTransactionType(withdrawalTransaction.getTransactionType());

            transactionHistoryResponseDtoList.add(transactionHistoryResponseDto);
        });

        return transactionHistoryResponseDtoList;
    }


}
