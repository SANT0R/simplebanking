package com.eteration.simplebanking.model;


import com.eteration.simplebanking.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "account", indexes = @Index(columnList = "accountNumber"))
@AllArgsConstructor
@Data
@Entity
public class Account extends BaseModel {
    public Account() {
    }

    public Account(Long id, String accountNumber, String owner, Double balance) {
        this.setId(id);
        this.setAccountNumber(accountNumber);
        this.setOwner(owner);
        this.setBalance(balance);
    }

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private Double balance;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


}
