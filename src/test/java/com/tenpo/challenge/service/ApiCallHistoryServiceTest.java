package com.tenpo.challenge.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.model.entity.ApiCallHistory;
import com.tenpo.challenge.model.entity.ErrorResponse;
import com.tenpo.challenge.repository.ApiCallHistoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApiCallHistoryServiceTest {

  @Mock
  private ApiCallHistoryRepository repository;

  @Mock
  private ObjectMapper mapper;

  @InjectMocks
  private ApiCallHistoryService underTest;

  @Test
  void whenSaveApiCallHistoryThenSaveApiCallHistory() throws JsonProcessingException {
    var body = mock(BigDecimal.class);
    var request = mock(HttpServletRequest.class);
    var response = mock(HttpServletResponse.class);
    var apiCallhistory = new ApiCallHistory("/api/v1/sum", 200);
    apiCallhistory.setResponseBody("10");
    // given
    given(request.getRequestURI()).willReturn(apiCallhistory.getRequestPath());
    given(response.getStatus()).willReturn(apiCallhistory.getHttpStatusCode());
    given(mapper.writeValueAsString(any(Object.class))).willReturn(apiCallhistory.getResponseBody());
    // when
    underTest.saveApiCallHistory(body, request.getRequestURI(), response.getStatus());
    // then
    verify(repository, times(1)).save(argThat(new ApiCallHistoryMatcher(apiCallhistory)));
  }

  @Test
  void whenSaveApiCallHistoryWithExceptionThenSaveApiCallHistoryWithErrorMessage() throws JsonProcessingException {
    var body = mock(Object.class);
    var request = mock(HttpServletRequest.class);
    var response = mock(HttpServletResponse.class);
    var errorResponse = new ErrorResponse("404", "No message available");
    var apiCallhistory = new ApiCallHistory("/api/v1/sum3", 404);
    apiCallhistory.setErrorMessage(errorResponse.toString());
    // given
    given(request.getRequestURI()).willReturn(apiCallhistory.getRequestPath());
    given(response.getStatus()).willReturn(apiCallhistory.getHttpStatusCode());
    given(mapper.writeValueAsString(any(Object.class))).willReturn(apiCallhistory.getErrorMessage());
    given(mapper.readValue(anyString(), eq(ErrorResponse.class)))
        .willReturn(errorResponse);
    // when
    underTest.saveApiCallHistory(body, request.getRequestURI(), response.getStatus());
    // then
    verify(repository, times(1)).save(argThat(new ApiCallHistoryMatcher(apiCallhistory)));
  }

  @Test
  void whenSaveApiCallHistoryWithException5xxThenNotSaveApiCallHistoryWithErrorMessage() throws JsonProcessingException {
    var body = mock(Object.class);
    var request = mock(HttpServletRequest.class);
    var response = mock(HttpServletResponse.class);
    var errorResponse = new ErrorResponse("500", "No message available");
    var apiCallhistory = new ApiCallHistory("/api/v1/substract", 500);
    apiCallhistory.setErrorMessage(errorResponse.toString());
    // given
    given(response.getStatus()).willReturn(apiCallhistory.getHttpStatusCode());
    // when
    underTest.saveApiCallHistory(body, request.getRequestURI(), response.getStatus());
    // then
    verify(repository, times(0)).save(argThat(new ApiCallHistoryMatcher(apiCallhistory)));
  }

  static class ApiCallHistoryMatcher implements ArgumentMatcher<ApiCallHistory> {

    private final ApiCallHistory left;

    public ApiCallHistoryMatcher(ApiCallHistory left) {
      this.left = left;
    }

    @Override
    public boolean matches(ApiCallHistory right) {
      return left.getHttpStatusCode().equals(right.getHttpStatusCode()) &&
          left.getRequestPath().equals(right.getRequestPath()) &&
          Optional.ofNullable(left.getResponseBody())
              .map(left -> left.equals(right.getResponseBody()))
              .orElse(true);
    }
  }
}
