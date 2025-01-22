package com.ademarporto.ba.controller.handler;

import static com.ademarporto.ba.exception.ErrorMessage.INVALID_PARAMETER_ERROR_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.INVALID_PARAMETER_ERROR_MESSAGE;
import static com.ademarporto.ba.exception.ErrorMessage.NOT_READABLE_REQUEST_BODY_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.NOT_READABLE_REQUEST_BODY_MESSAGE;

import com.ademarporto.ba.exception.AccountRequestNotFoundException;
import com.ademarporto.ba.exception.InputValidationException;
import com.ademarporto.ba.rest.spec.ErrorDto;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    logger.error(String.format("Request body exception. %n Cause [ %s ]", ex.getMessage()));

    return createResponseEntity(
        NOT_READABLE_REQUEST_BODY_CODE, NOT_READABLE_REQUEST_BODY_MESSAGE, status);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    return createResponseEntity(
        INVALID_PARAMETER_ERROR_CODE,
        String.format(
            INVALID_PARAMETER_ERROR_MESSAGE,
            Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField(),
            Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage()),
        status);
  }

  @ExceptionHandler(AccountRequestNotFoundException.class)
  protected ResponseEntity<Object> handleAccountNotFoundException(
      AccountRequestNotFoundException ex, WebRequest request) {
    return createResponseEntity(ex.getCode(), ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InputValidationException.class)
  protected ResponseEntity<Object> handleInputValidationException(
      InputValidationException ex, WebRequest request) {
    return createResponseEntity(ex.getCode(), ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<Object> createResponseEntity(
      String errorCode, String errorMessage, HttpStatusCode status) {
    var error = new ErrorDto();
    error.setCode(errorCode);
    error.setMessage(errorMessage);
    return new ResponseEntity<>(error, status);
  }
}
