package com.ademarporto.ba.controller;

import static com.ademarporto.ba.exception.ErrorMessage.ACCOUNT_NOT_FOUND_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.ACCOUNT_NOT_FOUND_MESSAGE;
import static com.ademarporto.ba.exception.ErrorMessage.DUPLICATED_ACCOUNT_ERROR_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.DUPLICATED_ACCOUNT_ERROR_MESSAGE;
import static com.ademarporto.ba.exception.ErrorMessage.INVALID_OPERATION_ERROR_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.INVALID_OPERATION_ERROR_MESSAGE;
import static com.ademarporto.ba.exception.ErrorMessage.INVALID_PARAMETER_ERROR_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.SUBMITTED_ACCOUNT_ERROR_CODE;
import static com.ademarporto.ba.exception.ErrorMessage.SUBMITTED_ACCOUNT_ERROR_MESSAGE;
import static com.ademarporto.ba.testutils.AccountRequestFactory.createAccountRequestDto;
import static com.ademarporto.ba.testutils.AccountRequestFactory.createAccountRequestEntity;
import static com.ademarporto.ba.testutils.TestConstants.REQUEST_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ademarporto.ba.model.AccountStatus;
import com.ademarporto.ba.repository.AccountRequestRepository;
import com.ademarporto.ba.rest.spec.AccountResponseDto;
import com.ademarporto.ba.rest.spec.AccountStatusDto;
import com.ademarporto.ba.rest.spec.ErrorDto;
import com.ademarporto.ba.testutils.TestBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class AccountRequestControllerIT extends TestBase {

  private static final String ACCOUNT_REQUEST_ENDPOINT = "/v1/account-requests";
  private static final String ACCOUNT_REQUEST_ENDPOINT_WITH_PARAMETER = "/v1/account-requests/%s";
  private static final String ACCOUNT_REQUEST_SUBMIT_ENDPOINT = "/v1/account-requests/%s/submit";

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private AccountRequestRepository repository;

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

  @Test
  void when_CreateNewAccountRequest_ThenReturn_Created() throws Exception {
    var request = createAccountRequestDto();
    mockMvc
        .perform(
            post(ACCOUNT_REQUEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());

    var storedAccountRequest = repository.findAll().stream().findFirst();

    assertThat(storedAccountRequest).isNotNull();
    assertTrue(storedAccountRequest.isPresent());
    assertThat(storedAccountRequest.get().getRequestId()).isNotNull();
    assertThat(storedAccountRequest.get().getName()).isEqualTo(request.getAccount().getName());
  }

  @Test
  void when_CreateNewAccountRequest_InvalidNameThenReturn_BadRequest() throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().setName(null);
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo("Invalid parameter: [ account.name ], cause: field [ must not be null ].");
  }

  @Test
  void when_CreateNewAccountRequest_InvalidAddressThenReturn_BadRequest() throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().setAddress(null);
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo("Invalid parameter: [ account.address ], cause: field [ must not be null ].");
  }

  @Test
  void when_CreateNewAccountRequest_InvalidAddressStreetNameThenReturn_BadRequest()
      throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().getAddress().setStreetName(null);
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo(
            "Invalid parameter: [ account.address.streetName ], cause: field [ must not be null ].");
  }

  @Test
  void when_CreateNewAccountRequest_InvalidAddressHouseNumberThenReturn_BadRequest()
      throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().getAddress().setHouseNumber(null);
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo(
            "Invalid parameter: [ account.address.houseNumber ], cause: field [ must not be null ].");
  }

  @Test
  void when_CreateNewAccountRequest_InvalidAddressNullPostCodeThenReturn_BadRequest()
      throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().getAddress().setPostCode(null);
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo(
            "Invalid parameter: [ account.address.postCode ], cause: field [ must not be null ].");
  }

  @Test
  void when_CreateNewAccountRequest_InvalidAddressPostCodeThenReturn_BadRequest() throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().getAddress().setPostCode("1234  ABC");
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo(
            "Invalid parameter: [ account.address.postCode ], cause: field [ must match \"^\\d{4} [A-Z]{2}$\" ].");
  }

  @Test
  void when_CreateNewAccountRequest_InvalidAddressCityThenReturn_BadRequest() throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().getAddress().setCity(null);
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo(
            "Invalid parameter: [ account.address.city ], cause: field [ must not be null ].");
  }

  @Test
  void when_CreateNewAccountRequest_InvalidDateOfBirthThenReturn_BadRequest() throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().setDateOfBirth(null);
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo(
            "Invalid parameter: [ account.dateOfBirth ], cause: field [ must not be null ].");
  }

  @Test
  void when_CreateNewAccountRequest_InvalidIdDocumentThenReturn_BadRequest() throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().setIdDocument(null);
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo("Invalid parameter: [ Id Document ], cause: field [ must not be null ].");
  }

  @Test
  void when_CreateNewAccountRequest_InvalidIdDocumentButStatusPausedThenReturn_ShouldPass()
      throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().setIdDocument(null);
    request.getAccount().setAccountStatus(AccountStatusDto.PAUSED);

    mockMvc
        .perform(
            post(ACCOUNT_REQUEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());

    var storedAccountRequest = repository.findAll().stream().findFirst();

    assertThat(storedAccountRequest).isNotNull();
    assertTrue(storedAccountRequest.isPresent());
    assertThat(storedAccountRequest.get().getRequestId()).isNotNull();
    assertThat(storedAccountRequest.get().getAccountStatus().name())
        .isEqualTo(request.getAccount().getAccountStatus().name());
  }

  @Test
  void when_CreateNewAccountRequest_InvalidAccountTypeThenReturn_BadRequest() throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().setAccountType(null);
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo("Invalid parameter: [ Account Type ], cause: field [ must not be null ].");
  }

  @Test
  void when_CreateNewAccountRequest_InvalidAccountTypeButStatusPausedThenReturn_ShouldPass()
      throws Exception {
    var request = createAccountRequestDto();
    request.getAccount().setAccountType(null);
    request.getAccount().setAccountStatus(AccountStatusDto.PAUSED);

    mockMvc
        .perform(
            post(ACCOUNT_REQUEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());

    var storedAccountRequest = repository.findAll().stream().findFirst();

    assertThat(storedAccountRequest).isNotNull();
    assertTrue(storedAccountRequest.isPresent());
    assertThat(storedAccountRequest.get().getRequestId()).isNotNull();
    assertThat(storedAccountRequest.get().getAccountStatus().name())
        .isEqualTo(request.getAccount().getAccountStatus().name());
  }

  @Test
  void when_CreateNewAccountRequest_DuplicatedThenReturn_BadRequest() throws Exception {
    var accountRequestEntity = createAccountRequestEntity();
    repository.save(accountRequestEntity);

    var request = createAccountRequestDto();
    var mvcResult =
        mockMvc
            .perform(
                post(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(DUPLICATED_ACCOUNT_ERROR_CODE);
    assertThat(response.getMessage()).isEqualTo(DUPLICATED_ACCOUNT_ERROR_MESSAGE);
  }

  @Test
  void when_UpdateAccountRequest_ThenReturn_Ok() throws Exception {
    var accountRequestEntity = createAccountRequestEntity();
    var storedAccountRequest = repository.save(accountRequestEntity);
    assertThat(storedAccountRequest.getAccountStatus()).isEqualTo(AccountStatus.IN_PROGRESS);

    var request = createAccountRequestDto();
    request.getAccount().setRequestId(storedAccountRequest.getRequestId());
    request.getAccount().setAccountStatus(AccountStatusDto.PAUSED);

    var mvcResult =
        mockMvc
            .perform(
                put(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, AccountResponseDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getAccount()).isNotNull();
    assertThat(response.getAccount().getRequestId()).isEqualTo(storedAccountRequest.getRequestId());
    assertThat(response.getAccount().getAccountStatus())
        .isEqualTo(request.getAccount().getAccountStatus());
  }

  @Test
  void when_UpdateAccountRequest_IfAccountAlreadySubmitted_ThenReturn_BadRequest()
      throws Exception {
    var accountRequestEntity = createAccountRequestEntity();
    accountRequestEntity.setAccountStatus(AccountStatus.SUBMITTED);
    var storedAccountRequest = repository.save(accountRequestEntity);

    var request = createAccountRequestDto();
    request.getAccount().setRequestId(storedAccountRequest.getRequestId());
    request.getAccount().setAccountStatus(AccountStatusDto.PAUSED);

    var mvcResult =
        mockMvc
            .perform(
                put(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(SUBMITTED_ACCOUNT_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo(
            String.format(SUBMITTED_ACCOUNT_ERROR_MESSAGE, storedAccountRequest.getRequestId()));
  }

  @Test
  void when_UpdateAccountRequest_InvalidOperation_ThenReturn_BadRequest() throws Exception {
    var accountRequestEntity = createAccountRequestEntity();
    var storedAccountRequest = repository.save(accountRequestEntity);

    var request = createAccountRequestDto();
    request.getAccount().setRequestId(storedAccountRequest.getRequestId());
    request.getAccount().setAccountStatus(AccountStatusDto.SUBMITTED);

    var mvcResult =
        mockMvc
            .perform(
                put(ACCOUNT_REQUEST_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_OPERATION_ERROR_CODE);
    assertThat(response.getMessage()).isEqualTo(INVALID_OPERATION_ERROR_MESSAGE);
  }

  @Test
  void when_DeleteAccountRequest_ThenReturn_NoContent() throws Exception {
    var accountRequestEntity = createAccountRequestEntity();
    var storedAccountRequest = repository.save(accountRequestEntity);

    mockMvc
        .perform(
            delete(
                    String.format(
                        ACCOUNT_REQUEST_ENDPOINT_WITH_PARAMETER,
                        storedAccountRequest.getRequestId()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    var response = repository.findById(storedAccountRequest.getRequestId());

    assertThat(response).isEmpty();
  }

  @Test
  void when_DeleteAccountRequest_AccountNotPresent_ThenReturn_BadRequest() throws Exception {
    var mvcResult =
        mockMvc
            .perform(
                delete(String.format(ACCOUNT_REQUEST_ENDPOINT_WITH_PARAMETER, REQUEST_ID))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(ACCOUNT_NOT_FOUND_CODE);
    assertThat(response.getMessage())
        .isEqualTo(String.format(ACCOUNT_NOT_FOUND_MESSAGE, REQUEST_ID));
  }

  @Test
  void when_GetAccountRequestById_ThenReturn_Ok() throws Exception {
    var accountRequestEntity = createAccountRequestEntity();
    var storedAccountRequest = repository.save(accountRequestEntity);

    var mvcResult =
        mockMvc
            .perform(
                get(String.format(
                        ACCOUNT_REQUEST_ENDPOINT_WITH_PARAMETER,
                        storedAccountRequest.getRequestId()))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, AccountResponseDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getAccount()).isNotNull();
    assertThat(response.getAccount().getRequestId()).isEqualTo(storedAccountRequest.getRequestId());
  }

  @Test
  void when_GetAccountRequestById_AccountNotPresent_ThenReturn_BadRequest() throws Exception {
    var mvcResult =
        mockMvc
            .perform(
                get(String.format(ACCOUNT_REQUEST_ENDPOINT_WITH_PARAMETER, REQUEST_ID))
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(ACCOUNT_NOT_FOUND_CODE);
    assertThat(response.getMessage())
        .isEqualTo(String.format(ACCOUNT_NOT_FOUND_MESSAGE, REQUEST_ID));
  }

  @Test
  void when_SubmitAccountRequest_ThenReturn_NoContent() throws Exception {
    var accountRequestEntity = createAccountRequestEntity();
    var storedAccountRequest = repository.save(accountRequestEntity);

    mockMvc
        .perform(
            get(String.format(ACCOUNT_REQUEST_SUBMIT_ENDPOINT, storedAccountRequest.getRequestId()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    var response = repository.findById(storedAccountRequest.getRequestId());

    assertTrue(response.isPresent());
    assertThat(response.get().getAccountStatus()).isEqualTo(AccountStatus.SUBMITTED);
  }

  @Test
  void when_SubmitAccountRequest_AccountNotPresent_ThenReturn_BadRequest() throws Exception {
    var mvcResult =
        mockMvc
            .perform(
                get(String.format(ACCOUNT_REQUEST_SUBMIT_ENDPOINT, REQUEST_ID))
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(ACCOUNT_NOT_FOUND_CODE);
    assertThat(response.getMessage())
        .isEqualTo(String.format(ACCOUNT_NOT_FOUND_MESSAGE, REQUEST_ID));
  }

  @Test
  void when_SubmitAccountRequest_AccountMissingIdAccount_ThenReturn_BadRequest() throws Exception {
    var accountRequestEntity = createAccountRequestEntity();
    accountRequestEntity.setIdDocument(null);
    var storedAccountRequest = repository.save(accountRequestEntity);

    var mvcResult =
        mockMvc
            .perform(
                get(String.format(
                        ACCOUNT_REQUEST_SUBMIT_ENDPOINT, storedAccountRequest.getRequestId()))
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo("Invalid parameter: [ Id Document ], cause: field [ must not be null ].");
  }

  @Test
  void when_SubmitAccountRequest_AccountMissingAccountType_ThenReturn_BadRequest()
      throws Exception {
    var accountRequestEntity = createAccountRequestEntity();
    accountRequestEntity.setAccountType(null);
    var storedAccountRequest = repository.save(accountRequestEntity);

    var mvcResult =
        mockMvc
            .perform(
                get(String.format(
                        ACCOUNT_REQUEST_SUBMIT_ENDPOINT, storedAccountRequest.getRequestId()))
                    .contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

    var response = objectMapper.readValue(mvcResult, ErrorDto.class);

    assertThat(response).isNotNull();
    assertThat(response.getCode()).isEqualTo(INVALID_PARAMETER_ERROR_CODE);
    assertThat(response.getMessage())
        .isEqualTo("Invalid parameter: [ Account Type ], cause: field [ must not be null ].");
  }
}
