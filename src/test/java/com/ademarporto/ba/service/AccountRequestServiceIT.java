package com.ademarporto.ba.service;

import static com.ademarporto.ba.testutils.AccountRequestFactory.createAccountRequest;
import static com.ademarporto.ba.testutils.AccountRequestFactory.createNewAccountRequest;
import static com.ademarporto.ba.testutils.TestConstants.ID_DOCUMENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ademarporto.ba.exception.AccountRequestNotFoundException;
import com.ademarporto.ba.exception.InputValidationException;
import com.ademarporto.ba.mapper.AccountRequestMapperImpl;
import com.ademarporto.ba.model.AccountStatus;
import com.ademarporto.ba.model.AccountType;
import com.ademarporto.ba.repository.AccountRequestRepository;
import com.ademarporto.ba.testutils.TestBase;
import com.ademarporto.ba.validation.AccountRequestValidationImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AccountRequestServiceIT extends TestBase {
  @Autowired private AccountRequestServiceImpl service;
  @Autowired private AccountRequestRepository repository;
  @Autowired private AccountRequestMapperImpl mapper;
  @Autowired private AccountRequestValidationImpl validation;

  @BeforeEach
  void tearDown() {
    repository.deleteAll();
  }

  @Test
  void testCreateAccountRequestSuccess() {
    // Given
    var accountRequest = createAccountRequest();

    // When
    var storedRequest = service.createAccountRequest(accountRequest);

    // Then
    assertNotNull(storedRequest);
    assertNotNull(storedRequest.requestId());
  }

  @Test
  void testCreateAccountRequestWhenStatusInProgressAndMissingIdDocumentShouldFail() {
    // Given
    var accountRequest = createAccountRequest(null, AccountType.CURRENT, AccountStatus.IN_PROGRESS);

    // When && Then
    assertThatExceptionOfType(InputValidationException.class)
        .isThrownBy(() -> service.createAccountRequest(accountRequest));
  }

  @Test
  void testCreateAccountRequestWhenStatusInProgressAndMissingAccountTypeShouldFail() {
    // Given
    var accountRequest = createAccountRequest(ID_DOCUMENT, null, AccountStatus.IN_PROGRESS);

    // When && Then
    assertThatExceptionOfType(InputValidationException.class)
        .isThrownBy(() -> service.createAccountRequest(accountRequest));
  }

  @Test
  void testCreateAccountRequestWhenStatusPausedAndMissingIdDocumentAndAccountTypeShouldPass() {
    // Given
    var accountRequest = createAccountRequest(null, null, AccountStatus.PAUSED);

    // When
    var storedRequest = service.createAccountRequest(accountRequest);

    // Then
    assertNotNull(storedRequest);
    assertNotNull(storedRequest.requestId());
  }

  @Test
  void testCreateAccountRequestWhenDuplicatedRequestShouldFail() {
    // Given
    var accountRequest = createAccountRequest();
    service.createAccountRequest(accountRequest);

    // When && Then
    assertThatExceptionOfType(InputValidationException.class)
        .isThrownBy(() -> service.createAccountRequest(accountRequest));
  }

  @Test
  void testUpdateAccountRequest() {
    // Given
    var accountRequest = createAccountRequest();
    var storedRequest = service.createAccountRequest(accountRequest);
    var updatedAccountRequest = createNewAccountRequest(storedRequest.requestId());
    // When
    var updatedRequest = service.updateAccountRequest(updatedAccountRequest);

    // Then
    assertNotNull(storedRequest);
    assertNotNull(updatedRequest);
    assertThat(updatedAccountRequest.requestId()).isEqualTo(updatedRequest.requestId());
    assertThat(updatedAccountRequest.accountType()).isEqualTo(updatedRequest.accountType());
    assertThat(updatedAccountRequest.accountStatus()).isEqualTo(updatedRequest.accountStatus());
  }

  @Test
  void testUpdateAccountRequestWhenStatusInProgressAndMissingIdDocumentShouldFail() {
    // Given
    var accountRequest = createAccountRequest(null, AccountType.CURRENT, AccountStatus.IN_PROGRESS);

    // When && Then
    assertThatExceptionOfType(InputValidationException.class)
        .isThrownBy(() -> service.updateAccountRequest(accountRequest));
  }

  @Test
  void testUpdateAccountRequestWhenStatusInProgressAndMissingAccountTypeShouldFail() {
    // Given
    var accountRequest = createAccountRequest(ID_DOCUMENT, null, AccountStatus.IN_PROGRESS);

    // When && Then
    assertThatExceptionOfType(InputValidationException.class)
        .isThrownBy(() -> service.updateAccountRequest(accountRequest));
  }

  @Test
  void testUpdateAccountRequestWhenAccountRequestNotPresentShouldFail() {
    // Given
    var accountRequest = createAccountRequest();

    // When && Then
    assertThatExceptionOfType(AccountRequestNotFoundException.class)
        .isThrownBy(() -> service.updateAccountRequest(accountRequest));
  }

  @Test
  void testUpdateAccountRequestWhenAccountRequestAlreadySubmittedShouldFail() {
    // Given
    var accountRequest =
        createAccountRequest(ID_DOCUMENT, AccountType.CURRENT, AccountStatus.SUBMITTED);
    var storedRequest = service.createAccountRequest(accountRequest);
    var updatedAccountRequest = createNewAccountRequest(storedRequest.requestId());

    // When && Then
    assertThatExceptionOfType(InputValidationException.class)
        .isThrownBy(() -> service.updateAccountRequest(updatedAccountRequest));
  }

  @Test
  void testDeleteAccountRequest() {
    // Given
    var accountRequest = createAccountRequest();
    var storedRequest = service.createAccountRequest(accountRequest);
    assertThat(storedRequest.requestId()).isNotNull();

    // When
    service.deleteAccountRequest(storedRequest.requestId());

    // Then
    var result = repository.findById(storedRequest.requestId());
    assertTrue(result.isEmpty());
  }

  @Test
  void testDeleteAccountRequestWhenRequestIsNotPresentShouldFail() {
    // Given
    var accountRequest = createAccountRequest();

    // When && Then
    assertThatExceptionOfType(AccountRequestNotFoundException.class)
        .isThrownBy(() -> service.deleteAccountRequest(accountRequest.requestId()));
  }

  @Test
  void fetchAccountRequestById() {
    // Given
    var accountRequest = createAccountRequest();
    var storedRequest = service.createAccountRequest(accountRequest);
    assertThat(storedRequest.requestId()).isNotNull();

    // When
    var result = service.fetchAccountRequestById(storedRequest.requestId());

    assertThat(result).isNotNull();
    assertThat(result.requestId()).isEqualTo(storedRequest.requestId());
  }

  @Test
  void fetchAccountRequestByIdWhenRequestIsNotPresentShouldFail() {
    var accountRequest = createAccountRequest();

    // When && Then
    assertThatExceptionOfType(AccountRequestNotFoundException.class)
        .isThrownBy(() -> service.fetchAccountRequestById(accountRequest.requestId()));
  }

  @Test
  void testSubmitAccountRequest() {
    // Given
    var accountRequest = createAccountRequest();
    var storedRequest = service.createAccountRequest(accountRequest);
    assertThat(storedRequest.accountStatus()).isEqualTo(AccountStatus.IN_PROGRESS);

    // When
    service.submitAccountRequest(storedRequest.requestId());

    // Then
    var result = repository.findById(storedRequest.requestId());
    assertTrue(result.isPresent());
    assertThat(result.get().getAccountStatus()).isEqualTo(AccountStatus.SUBMITTED);
  }

  @Test
  void testSubmitAccountRequestWhenRequestIsNotPresentShouldFail() {
    var accountRequest = createAccountRequest();

    // When && Then
    assertThatExceptionOfType(AccountRequestNotFoundException.class)
        .isThrownBy(() -> service.submitAccountRequest(accountRequest.requestId()));
  }
}
