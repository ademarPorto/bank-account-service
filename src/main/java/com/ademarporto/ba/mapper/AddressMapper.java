package com.ademarporto.ba.mapper;

import com.ademarporto.ba.model.Address;
import com.ademarporto.ba.repository.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AddressMapper {

    Address toAddressModel(com.ademarporto.ba.rest.spec.AddressDto address);

    AddressEntity toAddressEntity(Address address);
}
