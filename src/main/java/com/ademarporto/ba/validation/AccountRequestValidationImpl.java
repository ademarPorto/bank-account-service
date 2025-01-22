package com.ademarporto.ba.validation;

import static com.ademarporto.ba.exception.ErrorMessage.INVALID_OPERATION_ERROR_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.INVALID_OPERATION_ERROR_MESSAGE;
import static com.ademarporto.ba.exception.ErrorMessage.INVALID_PARAMETER_ERROR_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.INVALID_PARAMETER_ERROR_MESSAGE;

import com.ademarporto.ba.exception.InputValidationException;
import com.ademarporto.ba.model.AccountRequest;
import com.ademarporto.ba.model.AccountStatus;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class AccountRequestValidationImpl implements AccountRequestValidation {

  private static final String ERROR_MESSAGE = "must not be null";

  @Override
  public void validateAccountRequest(AccountRequest accountRequest) {
    if (AccountStatus.IN_PROGRESS.equals(accountRequest.accountStatus())) {
      if (Objects.isNull(accountRequest.idDocument())) {
        throw new InputValidationException(
            INVALID_PARAMETER_ERROR_CODE,
            String.format(INVALID_PARAMETER_ERROR_MESSAGE, "Id Document", ERROR_MESSAGE));
      }
      if (Objects.isNull(accountRequest.accountType())) {
        throw new InputValidationException(
            INVALID_PARAMETER_ERROR_CODE,
            String.format(INVALID_PARAMETER_ERROR_MESSAGE, "Account Type", ERROR_MESSAGE));
      }
    }
  }

  @Override
  public void validateUpdateAccountRequest(AccountRequest accountRequest) {
    validateAccountRequest(accountRequest);
    if (AccountStatus.SUBMITTED.equals(accountRequest.accountStatus())) {
      throw new InputValidationException(
          INVALID_OPERATION_ERROR_CODE, INVALID_OPERATION_ERROR_MESSAGE);
    }
  }
}
