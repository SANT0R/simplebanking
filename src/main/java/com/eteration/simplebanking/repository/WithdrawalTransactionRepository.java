package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalTransactionRepository extends JpaRepository<WithdrawalTransaction, Long> {
    List<WithdrawalTransaction> findAllByAccount_AccountNumber(String account_accountNumber);
    List<WithdrawalTransaction> findAllByDateBetween(Long date, Long date2);
    List<WithdrawalTransaction> findAllByAmountBetween(Double amount, Double amount2);
}
