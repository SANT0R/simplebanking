package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.dto.WithdrawalTransactionDto;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WithdrawalTransactionMapper {
    @Named("dtoToEntity")
    @Mapping(source = "accountDto", target = "account")
    WithdrawalTransaction dtoToEntity(WithdrawalTransactionDto dto);

    @Named("entityToDto")
    @Mapping(source = "account", target = "accountDto")
    WithdrawalTransactionDto entityToDto(WithdrawalTransaction entity);



    @IterableMapping (qualifiedByName = "dtoToEntity")
    List<WithdrawalTransaction> dtoToEntityList(List<WithdrawalTransactionDto> dtoList);

    @IterableMapping (qualifiedByName = "entityToDto")
    List<WithdrawalTransactionDto> entityToDtoList(List<WithdrawalTransaction> entityList);


}
