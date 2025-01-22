package com.ademarporto.ba.mapper;

import static com.ademarporto.ba.testutils.AccountRequestFactory.createAccountRequest;
import static com.ademarporto.ba.testutils.AccountRequestFactory.createAccountRequestDto;
import static com.ademarporto.ba.testutils.AccountRequestFactory.createAccountRequestEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ademarporto.ba.testutils.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountRequestMapperTest extends TestBase {

  @Autowired private AccountRequestMapperImpl accountMapper;

  @Autowired private AddressMapperImpl addressMapper;

  @Test
  void testFromSpecToAccountModel() {
    // Given
    var accountDto = createAccountRequestDto().getAccount();

    // When
    var accountRequestModel = accountMapper.toAccountModel(accountDto);

    // Then
    assertThat(accountRequestModel).isNotNull();
    assertThat(accountRequestModel.name()).isEqualTo(accountDto.getName());
    assertThat(accountRequestModel.address()).isNotNull();
    assertThat(accountRequestModel.address().streetName())
        .isEqualTo(accountDto.getAddress().getStreetName());
    assertThat(accountRequestModel.address().houseNumber())
        .isEqualTo(accountDto.getAddress().getHouseNumber());
    assertThat(accountRequestModel.address().postCode())
        .isEqualTo(accountDto.getAddress().getPostCode());
    assertThat(accountRequestModel.address().city()).isEqualTo(accountDto.getAddress().getCity());
    assertThat(accountRequestModel.dateOfBirth()).isEqualTo(accountDto.getDateOfBirth());
    assertThat(accountRequestModel.idDocument()).isEqualTo(accountDto.getIdDocument());
    assertThat(accountRequestModel.accountType().name())
        .isEqualTo(accountDto.getAccountType().name());
    assertThat(accountRequestModel.startingBalance()).isEqualTo(accountDto.getStartingBalance());
    assertThat(accountRequestModel.monthlySalary()).isEqualTo(accountDto.getMonthlySalary());
    assertThat(accountRequestModel.interestedInOtherProducts())
        .isEqualTo(accountDto.getInterestedInOtherProducts());
    assertThat(accountRequestModel.email()).isEqualTo(accountDto.getEmail());
  }

  @Test
  void testFromEntityToAccountModel() {
    // Given
    var accountEntity = createAccountRequestEntity();

    // When
    var accountRequestModel = accountMapper.toAccountModel(accountEntity);

    // Then
    assertThat(accountRequestModel).isNotNull();
    assertThat(accountRequestModel.name()).isEqualTo(accountEntity.getName());
    assertThat(accountRequestModel.address()).isNotNull();
    assertThat(accountRequestModel.address().streetName())
        .isEqualTo(accountEntity.getAddress().getStreetName());
    assertThat(accountRequestModel.address().houseNumber())
        .isEqualTo(accountEntity.getAddress().getHouseNumber());
    assertThat(accountRequestModel.address().postCode())
        .isEqualTo(accountEntity.getAddress().getPostCode());
    assertThat(accountRequestModel.address().city())
        .isEqualTo(accountEntity.getAddress().getCity());
    assertThat(accountRequestModel.dateOfBirth()).isEqualTo(accountEntity.getDateOfBirth());
    assertThat(accountRequestModel.idDocument()).isEqualTo(accountEntity.getIdDocument());
    assertThat(accountRequestModel.accountType().name())
        .isEqualTo(accountEntity.getAccountType().name());
    assertThat(accountRequestModel.startingBalance()).isEqualTo(accountEntity.getStartingBalance());
    assertThat(accountRequestModel.monthlySalary()).isEqualTo(accountEntity.getMonthlySalary());
    assertThat(accountRequestModel.interestedInOtherProducts())
        .isEqualTo(accountEntity.getInterestedInOtherProducts());
    assertThat(accountRequestModel.email()).isEqualTo(accountEntity.getEmail());
  }

  @Test
  void testFromAccountModelToAccountEntity() {
    // Given
    var accountRequestModel = createAccountRequest();

    // When
    var accountRequestEntity = accountMapper.toAccountEntity(accountRequestModel);

    // Then
    assertThat(accountRequestEntity).isNotNull();
    assertThat(accountRequestEntity.getName()).isEqualTo(accountRequestModel.name());
    assertThat(accountRequestEntity.getAddress()).isNotNull();
    assertThat(accountRequestEntity.getAddress().getStreetName())
        .isEqualTo(accountRequestModel.address().streetName());
    assertThat(accountRequestEntity.getAddress().getHouseNumber())
        .isEqualTo(accountRequestModel.address().houseNumber());
    assertThat(accountRequestEntity.getAddress().getPostCode())
        .isEqualTo(accountRequestModel.address().postCode());
    assertThat(accountRequestEntity.getAddress().getCity())
        .isEqualTo(accountRequestModel.address().city());
    assertThat(accountRequestEntity.getDateOfBirth()).isEqualTo(accountRequestModel.dateOfBirth());
    assertThat(accountRequestEntity.getIdDocument()).isEqualTo(accountRequestModel.idDocument());
    assertThat(accountRequestEntity.getAccountType().name())
        .isEqualTo(accountRequestModel.accountType().name());
    assertThat(accountRequestEntity.getStartingBalance())
        .isEqualTo(accountRequestModel.startingBalance());
    assertThat(accountRequestEntity.getMonthlySalary())
        .isEqualTo(accountRequestModel.monthlySalary());
    assertThat(accountRequestEntity.getInterestedInOtherProducts())
        .isEqualTo(accountRequestModel.interestedInOtherProducts());
    assertThat(accountRequestEntity.getEmail()).isEqualTo(accountRequestModel.email());
  }

  @Test
  void testFromAccountModelToAccountDto() {
    // Given
    var accountRequestModel = createAccountRequest();

    // When
    var accountRequestDto = accountMapper.toAccountSpec(accountRequestModel);

    // Then
    assertThat(accountRequestDto).isNotNull();
    assertThat(accountRequestDto.getName()).isEqualTo(accountRequestModel.name());
    assertThat(accountRequestDto.getAddress()).isNotNull();
    assertThat(accountRequestDto.getAddress().getStreetName())
        .isEqualTo(accountRequestModel.address().streetName());
    assertThat(accountRequestDto.getAddress().getHouseNumber())
        .isEqualTo(accountRequestModel.address().houseNumber());
    assertThat(accountRequestDto.getAddress().getPostCode())
        .isEqualTo(accountRequestModel.address().postCode());
    assertThat(accountRequestDto.getAddress().getCity())
        .isEqualTo(accountRequestModel.address().city());
    assertThat(accountRequestDto.getDateOfBirth()).isEqualTo(accountRequestModel.dateOfBirth());
    assertThat(accountRequestDto.getIdDocument()).isEqualTo(accountRequestModel.idDocument());
    assertThat(accountRequestDto.getAccountType().name())
        .isEqualTo(accountRequestModel.accountType().name());
    assertThat(accountRequestDto.getStartingBalance())
        .isEqualTo(accountRequestModel.startingBalance());
    assertThat(accountRequestDto.getMonthlySalary()).isEqualTo(accountRequestModel.monthlySalary());
    assertThat(accountRequestDto.getInterestedInOtherProducts())
        .isEqualTo(accountRequestModel.interestedInOtherProducts());
    assertThat(accountRequestDto.getEmail()).isEqualTo(accountRequestModel.email());
  }
}
