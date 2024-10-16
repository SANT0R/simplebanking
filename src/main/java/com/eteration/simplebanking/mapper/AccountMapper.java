package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.dto.AccountDto;
import com.eteration.simplebanking.model.Account;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {


    @Named("dtoToEntity")
    Account dtoToEntity(AccountDto dto);

    @Named("entityToDto")
    AccountDto entityToDto(Account entity);



    @IterableMapping (qualifiedByName = "dtoToEntity")
    List<Account> dtoToEntityList(List<AccountDto> dtoList);

    @IterableMapping (qualifiedByName = "entityToDto")
    List<AccountDto> entityToDtoList(List<Account> entityList);


}
