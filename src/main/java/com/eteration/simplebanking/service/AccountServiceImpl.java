package com.eteration.simplebanking.service;


import com.eteration.simplebanking.dto.AccountDto;
import com.eteration.simplebanking.exception.ApiRequestException;
import com.eteration.simplebanking.mapper.AccountMapper;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;


    @Override
    public void add(AccountDto account) {

        accountRepository.save(accountMapper.dtoToEntity(account));
    }

    @Override
    public void update(AccountDto accountDto) {

        getById(accountDto.getId());
        accountRepository.save(accountMapper.dtoToEntity(accountDto));

    }

    @Override
    public List<AccountDto> getAll() {
        return accountMapper.entityToDtoList(accountRepository.findAll());
    }



    @Override
    public AccountDto getById(Long id) {

        Optional<Account> entity = accountRepository.findById(id);
        if (entity.isPresent())     return accountMapper.entityToDto(entity.get());
        throw new ApiRequestException("Account not found");

    }

    @Override
    public AccountDto getByAccountNumber(String accountNumber) {

        Account Account = accountRepository.findByAccountNumber(accountNumber);
        return accountMapper.entityToDto (Account);
    }

    @Override
    public List<AccountDto> getByOwner(String owner) {

        List<Account> accounts = accountRepository.findAllByOwner(owner);
        return accountMapper.entityToDtoList (accounts);
    }

    @Override
    public List<AccountDto> getByBalance(Double lowerLimit, Double upperLimit) {

        List<Account> accounts = accountRepository.findAllByBalanceBetween(lowerLimit, upperLimit);
        return accountMapper.entityToDtoList (accounts);
    }

    @Override
    public void deleteAllByOwner(String owner) {

        List<Account> accounts = accountRepository.findAllByOwner(owner);
        if (!accounts.isEmpty()){
            accountRepository.deleteAll(accounts);
        }
        else {
            throw new ApiRequestException(
                    "Your operation could not be completed because the Account named Unknown Owner could not be found.",
                    HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @Override
    public void deleteAll() {

        accountRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {

        getById(id);
        Account entity = accountRepository.getById(id);
        accountRepository.delete(entity);
    }

}
