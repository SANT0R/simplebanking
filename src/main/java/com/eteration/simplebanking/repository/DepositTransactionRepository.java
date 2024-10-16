package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Long> {
    List<DepositTransaction> findAllByAccount_AccountNumber(String account_accountNumber);
    List<DepositTransaction> findAllByDateBetween(Long date, Long date2);
    List<DepositTransaction> findAllByAmountBetween(Double amount, Double amount2);
}
