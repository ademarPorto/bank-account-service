package com.ademarporto.ba.testutils;

import com.ademarporto.ba.model.AccountRequest;
import com.ademarporto.ba.model.AccountStatus;
import com.ademarporto.ba.model.AccountType;
import com.ademarporto.ba.model.Address;
import com.ademarporto.ba.repository.entity.AccountRequestEntity;
import com.ademarporto.ba.repository.entity.AddressEntity;
import com.ademarporto.ba.rest.spec.AccountDto;
import com.ademarporto.ba.rest.spec.AccountRequestDto;
import com.ademarporto.ba.rest.spec.AccountStatusDto;
import com.ademarporto.ba.rest.spec.AccountTypeDto;
import com.ademarporto.ba.rest.spec.AddressDto;

import java.util.UUID;

import static com.ademarporto.ba.testutils.TestConstants.ACCOUNT_STATUS;
import static com.ademarporto.ba.testutils.TestConstants.ACCOUNT_STATUS_1;
import static com.ademarporto.ba.testutils.TestConstants.ACCOUNT_TYPE;
import static com.ademarporto.ba.testutils.TestConstants.ACCOUNT_TYPE_1;
import static com.ademarporto.ba.testutils.TestConstants.CITY;
import static com.ademarporto.ba.testutils.TestConstants.CUSTOMER_NAME;
import static com.ademarporto.ba.testutils.TestConstants.DATE_OF_BIRTH;
import static com.ademarporto.ba.testutils.TestConstants.EMAIL;
import static com.ademarporto.ba.testutils.TestConstants.HOUSE_NUMBER;
import static com.ademarporto.ba.testutils.TestConstants.ID_DOCUMENT;
import static com.ademarporto.ba.testutils.TestConstants.INTERESTED_IN_OTHER_PRODUCTS;
import static com.ademarporto.ba.testutils.TestConstants.MONTHLY_SALARY;
import static com.ademarporto.ba.testutils.TestConstants.POST_CODE;
import static com.ademarporto.ba.testutils.TestConstants.REQUEST_ID;
import static com.ademarporto.ba.testutils.TestConstants.STARTING_BALANCE;
import static com.ademarporto.ba.testutils.TestConstants.STREET_NAME;

public class AccountRequestFactory {
    private AccountRequestFactory() {
    }

    public static AccountRequest createAccountRequest() {
        return createAccountRequest(REQUEST_ID, ID_DOCUMENT, ACCOUNT_TYPE, ACCOUNT_STATUS);
    }

    public static AccountRequest createAccountRequest(String idDocument,
                                                      AccountType accountType,
                                                      AccountStatus accountStatus) {
        return createAccountRequest(REQUEST_ID, idDocument, accountType, accountStatus);
    }


    public static AccountRequest createAccountRequest(UUID requestId,
                                                      String idDocument,
                                                      AccountType accountType,
                                                      AccountStatus accountStatus) {
        return new AccountRequest(requestId,
                CUSTOMER_NAME,
                DATE_OF_BIRTH,
                idDocument,
                createAddress(),
                accountType,
                STARTING_BALANCE,
                MONTHLY_SALARY,
                INTERESTED_IN_OTHER_PRODUCTS,
                EMAIL,
                accountStatus);
    }

    public static AccountRequest createNewAccountRequest(UUID requestId) {
        return new AccountRequest(requestId,
                CUSTOMER_NAME,
                DATE_OF_BIRTH,
                ID_DOCUMENT,
                createAddress(),
                ACCOUNT_TYPE_1,
                STARTING_BALANCE,
                MONTHLY_SALARY,
                INTERESTED_IN_OTHER_PRODUCTS,
                EMAIL,
                ACCOUNT_STATUS_1);
    }

    public static Address createAddress() {
        return new Address(STREET_NAME,
                HOUSE_NUMBER,
                POST_CODE,
                CITY);
    }

    public static AccountRequestDto createAccountRequestDto() {

        var accountDto = new AccountDto();
        accountDto.setName(CUSTOMER_NAME);
        accountDto.setDateOfBirth(DATE_OF_BIRTH);
        accountDto.setIdDocument(ID_DOCUMENT);
        accountDto.setAddress(createAddressDto());
        accountDto.setAccountType(AccountTypeDto.CURRENT);
        accountDto.setStartingBalance(STARTING_BALANCE);
        accountDto.setMonthlySalary(MONTHLY_SALARY);
        accountDto.setInterestedInOtherProducts(INTERESTED_IN_OTHER_PRODUCTS);
        accountDto.setEmail(EMAIL);
        accountDto.setAccountStatus(AccountStatusDto.IN_PROGRESS);
        var requestDto = new AccountRequestDto();
        requestDto.setAccount(accountDto);
        return requestDto;
    }

    public static AddressDto createAddressDto() {
        var address = new AddressDto();
        address.setStreetName(STREET_NAME);
        address.setHouseNumber(HOUSE_NUMBER);
        address.setPostCode(POST_CODE);
        address.setCity(CITY);
        return address;
    }

    public static AccountRequestEntity createAccountRequestEntity() {

        var accountEntity = new AccountRequestEntity();
        accountEntity.setName(CUSTOMER_NAME);
        accountEntity.setDateOfBirth(DATE_OF_BIRTH);
        accountEntity.setIdDocument(ID_DOCUMENT);
        accountEntity.setAddress(createAddressEntity());
        accountEntity.setAccountType(AccountType.CURRENT);
        accountEntity.setStartingBalance(STARTING_BALANCE);
        accountEntity.setMonthlySalary(MONTHLY_SALARY);
        accountEntity.setInterestedInOtherProducts(INTERESTED_IN_OTHER_PRODUCTS);
        accountEntity.setEmail(EMAIL);
        accountEntity.setAccountStatus(AccountStatus.IN_PROGRESS);
        return accountEntity;
    }

    public static AddressEntity createAddressEntity() {
        var address = new AddressEntity();
        address.setStreetName(STREET_NAME);
        address.setHouseNumber(HOUSE_NUMBER);
        address.setPostCode(POST_CODE);
        address.setCity(CITY);
        return address;
    }
}
