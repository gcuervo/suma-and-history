package com.tenpo.challenge.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.model.dto.ApiCallHistoryDto;
import com.tenpo.challenge.model.dto.ApiCallHistoryRequest;
import com.tenpo.challenge.model.entity.ApiCallHistory;
import com.tenpo.challenge.model.entity.ErrorResponse;
import com.tenpo.challenge.repository.ApiCallHistoryRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApiCallHistoryService {

  private static final Logger logger = LoggerFactory.getLogger(ApiCallHistoryService.class);

  private final ApiCallHistoryRepository repository;
  private final ObjectMapper mapper;

  public ApiCallHistoryService(
      ApiCallHistoryRepository apiCallHistoryRepository,
      ObjectMapper mapper
  ) {
    this.repository = apiCallHistoryRepository;
    this.mapper = mapper;
  }

  @Async
  @Transactional
  public void saveApiCallHistory(
      Object body,
      String requestUri,
      int status
  ) {
    if (isServerError(status)) { // Solo los errores de la serie 4xx
      return;
    }
    var apiCall = buildApiCallHistory(body, requestUri, status);
    repository.save(apiCall);
  }

  @Transactional(readOnly = true)
  public Page<ApiCallHistoryDto> findAllApiCallHistory(ApiCallHistoryRequest request) {
    return repository.findByCreatedDateBetween(
            request.getStartDate(),
            request.getEndDate(),
            PageRequest.of(request.getPage(), request.getSize())
        )
        .map(ApiCallHistoryDto::new);
  }

  private ApiCallHistory buildApiCallHistory(Object body, String requestUri, int status) {
    var apiCall = new ApiCallHistory(requestUri, status);
    var bodyString = getBodyString(body);
    if (bodyString.isEmpty()) {
      return apiCall;
    }
    if (isClientError(status)) {
      getErrorMessage(bodyString.get()).ifPresent(apiCall::setErrorMessage);
    } else {
      apiCall.setResponseBody(bodyString.get());
    }
    return apiCall;
  }

  private Optional<String> getBodyString(Object body) {
    try {
      return Optional.ofNullable(mapper.writeValueAsString(body));
    } catch (JsonProcessingException e) {
      logger.error("Error parsing response body: {}", body, e);
      return Optional.empty();
    }
  }

  private Optional<String> getErrorMessage(String bodyString) {
    try {
      return Optional.ofNullable(mapper.readValue(bodyString, ErrorResponse.class))
          .map(ErrorResponse::toString);
    } catch (JsonProcessingException e) {
      logger.error("Error parsing response body: {}", bodyString, e);
      return Optional.empty();
    }
  }

  private boolean isClientError(int status) {
    return status >= HttpStatus.BAD_REQUEST.value()
        && status < HttpStatus.INTERNAL_SERVER_ERROR.value();
  }

  private boolean isServerError(int status) {
    return status >= HttpStatus.INTERNAL_SERVER_ERROR.value();
  }
}
