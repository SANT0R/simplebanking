package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.dto.DepositTransactionDto;
import com.eteration.simplebanking.model.DepositTransaction;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepositTransactionMapper {
    @Named("dtoToEntity")
    @Mapping(source = "accountDto", target = "account")
    DepositTransaction dtoToEntity(DepositTransactionDto dto);

    @Named("entityToDto")
    @Mapping(source = "account", target = "accountDto")
    DepositTransactionDto entityToDto(DepositTransaction entity);



    @IterableMapping (qualifiedByName = "dtoToEntity")
    List<DepositTransaction> dtoToEntityList(List<DepositTransactionDto> dtoList);

    @IterableMapping (qualifiedByName = "entityToDto")
    List<DepositTransactionDto> entityToDtoList(List<DepositTransaction> entityList);


}
