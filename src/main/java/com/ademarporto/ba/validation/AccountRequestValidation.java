package com.ademarporto.ba.validation;

import com.ademarporto.ba.model.AccountRequest;

public interface AccountRequestValidation {

    void validateAccountRequest(AccountRequest accountRequest);

    void validateUpdateAccountRequest(AccountRequest accountRequest);
}
