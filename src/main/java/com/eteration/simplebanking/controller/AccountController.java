package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.AccountDto;
import com.eteration.simplebanking.dto.BetweenDto;
import com.eteration.simplebanking.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@Api(value="User Account Controller")
@RequiredArgsConstructor
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("add")
    @ApiOperation(value = "add")
    public void add(@RequestBody AccountDto accountDto) {
        accountService.add(accountDto);
    }

    @PutMapping("update")
    @ApiOperation(value = "update")
    public void update(@RequestBody AccountDto accountDto) {
        accountService.update(accountDto);
    }

    @DeleteMapping("delete")
    @ApiOperation(value = "delete")
    public void delete(@RequestParam Long id) {
        accountService.deleteById(id);
    }

    @DeleteMapping("deleteAllByOwner/{owner}")
    @ApiOperation(value = "deleteAllByOwner")
    public void deleteAllByOwner(@PathVariable String owner) {
        accountService.deleteAllByOwner(owner);
    }

    @DeleteMapping("deleteAll")
    @ApiOperation(value = "deleteAll")
    public void deleteAll() {
        accountService.deleteAll();
    }

    @GetMapping("get/{accountId}")
    @ApiOperation(value = "get")
    public Object get(@PathVariable Long accountId) {
        return accountService.getById(accountId);
    }

    @GetMapping("getByAccountNumber/{accountNumber}")
    @ApiOperation(value = "getByAccountNumber")
    public Object getByAccountNumber(@PathVariable String accountNumber) {
        return accountService.getByAccountNumber(accountNumber);
    }

    @GetMapping("getByOwner/{owner}")
    @ApiOperation(value = "getByOwner")
    public List<AccountDto> getByOwner(@PathVariable String owner) {
        return accountService.getByOwner(owner);
    }

    @GetMapping("getByBalance")
    @ApiOperation(value = "getByBalance")
    public List<AccountDto> getByBalance(@RequestBody BetweenDto betweenDto) {
        return accountService.getByBalance(betweenDto.getValue1(), betweenDto.getValue2());
    }


    @GetMapping("getAll")
    @ApiOperation(value = "getAll")
    public List<AccountDto> getAll() {
        return accountService.getAll();
    }

}