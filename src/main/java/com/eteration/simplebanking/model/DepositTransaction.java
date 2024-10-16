package com.eteration.simplebanking.model;


import com.eteration.simplebanking.enums.TransactionTypeEnum;
import javax.persistence.*;

import com.eteration.simplebanking.model.base.Transaction;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Table(name = "deposit_transaction", indexes = @Index(columnList = "account"))
@Data
@Entity
public class DepositTransaction  extends Transaction {

    public DepositTransaction(Long id, Account account, String accountNumber, Long date, Double amount) {
        this.setId(id);
        this.setAccountNumber(accountNumber);
        this.setAccount(account);
        this.setDate(date);
        this.setAmount(amount);
    }

    public DepositTransaction() {

    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    private final TransactionTypeEnum transactionType = TransactionTypeEnum.DEPOSIT;
}
