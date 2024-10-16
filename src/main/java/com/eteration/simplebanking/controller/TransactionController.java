package com.eteration.simplebanking.controller;


import com.eteration.simplebanking.dto.DepositTransactionDto;
import com.eteration.simplebanking.dto.WithdrawalTransactionDto;
import com.eteration.simplebanking.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@Api(value="User Transaction Controller")
@RequiredArgsConstructor
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @GetMapping("getAllByAccount/{accountNumber}")
    @ApiOperation(value = "getAllByAccount")
    public Object getAllByAccount(@PathVariable String accountNumber) {
        return transactionService.getTransactionHistory(accountNumber);
    }


    @PostMapping("deposit")
    @ApiOperation(value = "deposit")
    public void deposit(@RequestBody DepositTransactionDto depositTransactionDto) {

        transactionService.doTransaction(depositTransactionDto);
    }


    @PostMapping("withdrawal")
    @ApiOperation(value = "withdrawal")
    public void withdrawal (@RequestBody WithdrawalTransactionDto withdrawalTransactionDto) {
        
        transactionService.doTransaction(withdrawalTransactionDto);
    }
}
