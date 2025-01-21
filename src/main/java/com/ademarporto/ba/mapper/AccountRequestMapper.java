package com.ademarporto.ba.mapper;

import com.ademarporto.ba.model.AccountRequest;
import com.ademarporto.ba.repository.entity.AccountRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, uses = AddressMapper.class)
public interface AccountRequestMapper {

    AccountRequest toAccountModel(com.ademarporto.ba.rest.spec.AccountDto account);

    AccountRequest toAccountModel(AccountRequestEntity entity);

    com.ademarporto.ba.rest.spec.AccountDto toAccountSpec(AccountRequest accountRequest);

    @Mapping(target = "requestId", ignore = true)
    AccountRequestEntity toAccountEntity(AccountRequest accountRequest);


}
