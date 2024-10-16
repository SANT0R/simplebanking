package com.eteration.simplebanking.dto;

import com.eteration.simplebanking.dto.base.TransactionDto;
import com.eteration.simplebanking.enums.TransactionTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="TransactionHistoryResponseDto")
public class TransactionHistoryResponseDto extends TransactionDto {

    @ApiModelProperty(value="Transaction Type Enum")
    private TransactionTypeEnum transactionType;

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }
}
