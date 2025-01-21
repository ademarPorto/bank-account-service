package com.ademarporto.ba.service;

import com.ademarporto.ba.model.AccountRequest;

import java.util.UUID;

public interface AccountRequestService {

    AccountRequest createAccountRequest(AccountRequest accountRequest);

    AccountRequest updateAccountRequest(AccountRequest accountRequest);

    void deleteAccountRequest(UUID requestId);

    AccountRequest fetchAccountRequestById(UUID requestId);

    void submitAccountRequest(UUID requestId);

}
