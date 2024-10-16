package com.eteration.simplebanking.model;


import com.eteration.simplebanking.enums.TransactionTypeEnum;
import javax.persistence.*;

import com.eteration.simplebanking.model.base.Transaction;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "withdrawal_transaction", indexes = @Index(columnList = "account"))
@Data
@Entity
public class WithdrawalTransaction extends Transaction {
    private final TransactionTypeEnum transactionType = TransactionTypeEnum.WITHDRAWAL;


    public WithdrawalTransaction(Long id, Account account, String accountNumber, Long date, Double amount) {
        this.setId(id);
        this.setAccountNumber(accountNumber);
        this.setAccount(account);
        this.setDate(date);
        this.setAmount(amount);
    }

    public WithdrawalTransaction() {

    }
    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }
}


