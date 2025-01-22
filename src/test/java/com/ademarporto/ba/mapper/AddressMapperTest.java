package com.ademarporto.ba.mapper;

import static com.ademarporto.ba.testutils.AccountRequestFactory.createAddress;
import static com.ademarporto.ba.testutils.AccountRequestFactory.createAddressDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ademarporto.ba.testutils.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressMapperTest extends TestBase {

  @Autowired private AddressMapperImpl addressMapper;

  @Test
  void testFromSpecToAddressModel() {
    // Given
    var addressDto = createAddressDto();

    // When
    var addressModelModel = addressMapper.toAddressModel(addressDto);

    // Then
    assertThat(addressModelModel).isNotNull();
    assertThat(addressModelModel.streetName()).isEqualTo(addressDto.getStreetName());
    assertThat(addressModelModel.houseNumber()).isEqualTo(addressDto.getHouseNumber());
    assertThat(addressModelModel.postCode()).isEqualTo(addressDto.getPostCode());
    assertThat(addressModelModel.city()).isEqualTo(addressDto.getCity());
  }

  @Test
  void testFromAddressModelToAddressEntity() {
    // Given
    var addressModel = createAddress();

    // When
    var addressEntity = addressMapper.toAddressEntity(addressModel);

    // Then
    assertThat(addressEntity).isNotNull();
    assertThat(addressEntity.getStreetName()).isEqualTo(addressModel.streetName());
    assertThat(addressEntity.getHouseNumber()).isEqualTo(addressModel.houseNumber());
    assertThat(addressEntity.getPostCode()).isEqualTo(addressModel.postCode());
    assertThat(addressEntity.getCity()).isEqualTo(addressModel.city());
  }
}
