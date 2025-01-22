package com.ademarporto.ba.service;

import static com.ademarporto.ba.exception.ErrorMessage.ACCOUNT_NOT_FOUND_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.ACCOUNT_NOT_FOUND_MESSAGE;
import static com.ademarporto.ba.exception.ErrorMessage.DUPLICATED_ACCOUNT_ERROR_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.DUPLICATED_ACCOUNT_ERROR_MESSAGE;
import static com.ademarporto.ba.exception.ErrorMessage.SUBMITTED_ACCOUNT_ERROR_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.SUBMITTED_ACCOUNT_ERROR_MESSAGE;

import com.ademarporto.ba.exception.AccountRequestNotFoundException;
import com.ademarporto.ba.exception.InputValidationException;
import com.ademarporto.ba.mapper.AccountRequestMapper;
import com.ademarporto.ba.model.AccountRequest;
import com.ademarporto.ba.model.AccountStatus;
import com.ademarporto.ba.repository.AccountRequestRepository;
import com.ademarporto.ba.repository.entity.AccountRequestEntity;
import com.ademarporto.ba.validation.AccountRequestValidation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRequestServiceImpl implements AccountRequestService {

  private final AccountRequestRepository repository;
  private final AccountRequestMapper mapper;
  private final AccountRequestValidation validation;

  @Override
  public AccountRequest createAccountRequest(AccountRequest accountRequest) {
    validation.validateAccountRequest(accountRequest);
    failIfAccountRequestIsPresent(accountRequest);

    var accountEntity = mapper.toAccountEntity(accountRequest);
    var savedAccount = repository.save(accountEntity);
    return mapper.toAccountModel(savedAccount);
  }

  @Override
  public AccountRequest updateAccountRequest(AccountRequest accountRequest) {
    validation.validateUpdateAccountRequest(accountRequest);
    var storedAccount = failIfAccountRequestNotPresent(accountRequest.requestId());
    failIfAccountRequestIsSubmitted(storedAccount);

    var accountEntity = mapper.toAccountEntity(accountRequest);
    accountEntity.setRequestId(storedAccount.getRequestId());
    var updatedAccount = repository.save(accountEntity);

    return mapper.toAccountModel(updatedAccount);
  }

  @Override
  public void deleteAccountRequest(UUID requestId) {
    failIfAccountRequestNotPresent(requestId);
    repository.deleteById(requestId);
  }

  @Override
  public AccountRequest fetchAccountRequestById(UUID requestId) {
    var accountEntity = failIfAccountRequestNotPresent(requestId);
    return mapper.toAccountModel(accountEntity);
  }

  @Override
  public void submitAccountRequest(UUID requestId) {
    var storedAccount = failIfAccountRequestNotPresent(requestId);
    var accountRequest = mapper.toAccountModel(storedAccount);
    validation.validateAccountRequest(accountRequest);

    storedAccount.setAccountStatus(AccountStatus.SUBMITTED);
    repository.save(storedAccount);
  }

  private void failIfAccountRequestIsPresent(AccountRequest accountRequest) {
    repository
        .findAccountRequest(
            accountRequest.name(),
            accountRequest.dateOfBirth(),
            accountRequest.address().postCode(),
            accountRequest.address().houseNumber())
        .ifPresent(
            a -> {
              throw new InputValidationException(
                  DUPLICATED_ACCOUNT_ERROR_CODE, String.format(DUPLICATED_ACCOUNT_ERROR_MESSAGE));
            });
  }

  private AccountRequestEntity failIfAccountRequestNotPresent(UUID requestId) {
    return repository
        .findById(requestId)
        .orElseThrow(
            () ->
                new AccountRequestNotFoundException(
                    ACCOUNT_NOT_FOUND_CODE, String.format(ACCOUNT_NOT_FOUND_MESSAGE, requestId)));
  }

  private void failIfAccountRequestIsSubmitted(AccountRequestEntity accountRequest) {
    if (AccountStatus.SUBMITTED.equals(accountRequest.getAccountStatus())) {
      throw new InputValidationException(
          SUBMITTED_ACCOUNT_ERROR_CODE,
          String.format(SUBMITTED_ACCOUNT_ERROR_MESSAGE, accountRequest.getRequestId()));
    }
  }
}
