package com.eteration.simplebanking.dto;


import com.eteration.simplebanking.dto.base.TransactionDto;
import com.eteration.simplebanking.enums.TransactionTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="DepositTransactionDto")
public class DepositTransactionDto extends TransactionDto {
    public DepositTransactionDto() {
    }

    @ApiModelProperty(value="User TransactionTypeEnum")
    private final TransactionTypeEnum transactionType = TransactionTypeEnum.DEPOSIT;

    public DepositTransactionDto(long id, String accountNumber, double amount, AccountDto account) {
        this.setId(id);
        this.setAccountNumber(accountNumber);
        this.setAmount(amount);
        this.setAccount(account);
    }
}
