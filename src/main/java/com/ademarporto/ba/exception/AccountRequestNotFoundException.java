package com.ademarporto.ba.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRequestNotFoundException extends RuntimeException {
  private final String code;

  public AccountRequestNotFoundException(String code, String message) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
