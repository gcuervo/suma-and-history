package com.tenpo.challenge.model.dto;

import com.tenpo.challenge.model.entity.ApiCallHistory;
import java.time.OffsetDateTime;

public class ApiCallHistoryDto {

  private OffsetDateTime createdDate;
  private String requestPath;
  private String responseBody;
  private Integer httpStatusCode;
  private String errorMessage;


  private ApiCallHistoryDto() {
  }

  public ApiCallHistoryDto(ApiCallHistory apiCallHistory) {
    this.createdDate = apiCallHistory.getCreatedDate();
    this.requestPath = apiCallHistory.getRequestPath();
    this.responseBody = apiCallHistory.getResponseBody();
    this.httpStatusCode = apiCallHistory.getHttpStatusCode();
    this.errorMessage = apiCallHistory.getErrorMessage();
  }

  public OffsetDateTime getCreatedDate() {
    return createdDate;
  }

  public String getRequestPath() {
    return requestPath;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
