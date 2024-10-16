package com.eteration.simplebanking.dto.base;

import com.eteration.simplebanking.dto.AccountDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="User Api Doc",description="Model")
public abstract class TransactionDto extends BaseModelDto {
    @ApiModelProperty(value="User account number")
    private String accountNumber;

    private AccountDto account;

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public AccountDto getAccountDto() {
        return account;
    }

    public void setAccountDto(AccountDto accountDto) {
        this.account = accountDto;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @ApiModelProperty(value="User date")
    private Long date;

    @ApiModelProperty(value="User amount")
    private Double amount;
}
