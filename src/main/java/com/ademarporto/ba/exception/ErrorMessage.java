package com.ademarporto.ba.exception;

public class ErrorMessage {

  private ErrorMessage() {}

  public static final String NOT_READABLE_REQUEST_BODY_CODE = "ERROR_001";
  public static final String NOT_READABLE_REQUEST_BODY_MESSAGE = "Malformed JSON request.";
  public static final String INVALID_PARAMETER_ERROR_CODE = "ERROR_002";
  public static final String INVALID_PARAMETER_ERROR_MESSAGE =
      "Invalid parameter: [ %s ], cause: field [ %s ].";
  public static final String ACCOUNT_NOT_FOUND_CODE = "ERROR_003";
  public static final String ACCOUNT_NOT_FOUND_MESSAGE =
      "Not found any account request with id [ %s ]";

  public static final String DUPLICATED_ACCOUNT_ERROR_CODE = "ERROR_004";
  public static final String DUPLICATED_ACCOUNT_ERROR_MESSAGE =
      "An account request for this customer was already created.";

  public static final String SUBMITTED_ACCOUNT_ERROR_CODE = "ERROR_005";
  public static final String SUBMITTED_ACCOUNT_ERROR_MESSAGE =
      "Cannot update the account request with id [ %s ], "
          + "account request is in submitted status.";

  public static final String INVALID_OPERATION_ERROR_CODE = "ERROR_006";
  public static final String INVALID_OPERATION_ERROR_MESSAGE =
      "Cannot update the account status to SUBMITTED.";
}
