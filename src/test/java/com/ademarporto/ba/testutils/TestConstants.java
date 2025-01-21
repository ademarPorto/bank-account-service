package com.ademarporto.ba.testutils;

import com.ademarporto.ba.model.AccountStatus;
import com.ademarporto.ba.model.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TestConstants {

  private TestConstants() {}

  public static final UUID REQUEST_ID = UUID.fromString("2b31cf3c-1d3f-41d1-b857-0b69ee816603");
  public static final UUID REQUEST_ID_2 = UUID.fromString("83a73c9f-c57b-4297-b677-dd58655ee291");
  public static final String CUSTOMER_NAME = "test";
  public static final String ID_DOCUMENT = "ID_DOCUMENT";
  public static final AccountType ACCOUNT_TYPE = AccountType.CURRENT;
  public static final AccountType ACCOUNT_TYPE_1 = AccountType.SAVINGS;
  public static final LocalDate DATE_OF_BIRTH = LocalDate.parse("2000-01-20");
  public static final BigDecimal STARTING_BALANCE = new BigDecimal("1.000");
  public static final BigDecimal MONTHLY_SALARY = new BigDecimal("6.000");
  public static final boolean INTERESTED_IN_OTHER_PRODUCTS = true;
  public static final String EMAIL = "test@test.com";
  public static final AccountStatus ACCOUNT_STATUS = AccountStatus.IN_PROGRESS;
  public static final AccountStatus ACCOUNT_STATUS_1 = AccountStatus.IN_PROGRESS;
  public static final String STREET_NAME = "Street Name";
  public static final String HOUSE_NUMBER = "70";
  public static final String POST_CODE = "1234 AB";
  public static final String CITY = "Amsterdam";

}
