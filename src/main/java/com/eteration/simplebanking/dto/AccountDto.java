package com.eteration.simplebanking.dto;

import com.eteration.simplebanking.dto.base.BaseModelDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AccountDto")
public class AccountDto extends BaseModelDto {
    @ApiModelProperty(value="User owner")
    private String owner;

    @ApiModelProperty(value="User accountNumber")
    private String accountNumber;

    @ApiModelProperty(value="User balance")
    private Double balance;

    public AccountDto(long id, String accountNumber, String owner, double balance) {
        this.setId(id);
        this.setAccountNumber(accountNumber);
        this.setOwner(owner);
        this.setBalance(balance);
    }

    public AccountDto() {

    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
