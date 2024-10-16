package com.eteration.simplebanking.service;


import com.eteration.simplebanking.dto.AccountDto;

import java.util.List;

public interface AccountService {

    /**
     * Add an account
     * @param account -
     */
    void add(AccountDto account);

    /**
     * Update an account
     * @param account -
     */
    void update(AccountDto account);

    /**
     * Get all accounts
     * @return List<AccountDto>
     */
    List<AccountDto> getAll();

    /**
     * Get an account by id
     * @param id -
     * @return AccountDto
     */
    AccountDto getById(Long id);

    /**
     * Get an account by accountNumber
     * @param accountNumber -
     * @return AccountDto
     */
    AccountDto getByAccountNumber(String accountNumber);

    /**
     * Get an account by owner
     * @param owner -
     * @return AccountDto
     */
    List<AccountDto> getByOwner(String owner);

    /**
     * Get all accounts by between two balance
     * @param lowerLimit -
     * @param upperLimit -
     * @return AccountDto
     */
    List<AccountDto> getByBalance(Double lowerLimit, Double upperLimit);

    /**
     * Delete an account by owner
     * @param owner -
     */
    void deleteAllByOwner(String owner);

    /**
     * Delete all accounts
     */
    void deleteAll();

    /**
     * Delete an account by id
     * @param id -
     */
    void deleteById(Long id);
}
