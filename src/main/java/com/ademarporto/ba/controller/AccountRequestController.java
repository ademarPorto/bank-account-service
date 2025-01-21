package com.ademarporto.ba.controller;

import com.ademarporto.ba.mapper.AccountRequestMapper;
import com.ademarporto.ba.rest.api.AccountRequestControllerApi;
import com.ademarporto.ba.rest.spec.AccountRequestDto;
import com.ademarporto.ba.rest.spec.AccountResponseDto;
import com.ademarporto.ba.service.AccountRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountRequestController implements AccountRequestControllerApi {

    private final AccountRequestService service;
    private final AccountRequestMapper mapper;

    @Override
    public ResponseEntity<AccountResponseDto> createAccountRequest(
            @Valid @RequestBody AccountRequestDto accountRequestDto) {
        log.info("Hit [createAccount] endpoint.");

        var account = mapper.toAccountModel(accountRequestDto.getAccount());
        var storedAccount = service.createAccountRequest(account);

        var response = new AccountResponseDto();
        response.setAccount(mapper.toAccountSpec(storedAccount));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AccountResponseDto> updateAccountRequest(@Valid @RequestBody AccountRequestDto accountRequest) {
        log.info("Hit [updateAccount] endpoint.");

        var account = mapper.toAccountModel(accountRequest.getAccount());
        var storedAccount = service.updateAccountRequest(account);

        var response = new AccountResponseDto();
        response.setAccount(mapper.toAccountSpec(storedAccount));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteAccountRequest(@PathVariable("request-id") UUID requestId) {
        log.info("Hit [deleteAccount] endpoint.");
        service.deleteAccountRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AccountResponseDto> getAccountRequestById(@PathVariable("request-id") UUID requestId) {
        log.info("Hit [getAccount] endpoint.");

        var account = service.fetchAccountRequestById(requestId);
        var response = new AccountResponseDto();
        response.setAccount(mapper.toAccountSpec(account));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> submitAccountRequest(@PathVariable("request-id") UUID requestId) {
        log.info("Hit [submitAccountRequest] endpoint.");
        service.submitAccountRequest(requestId);
        return ResponseEntity.noContent().build();
    }

}
