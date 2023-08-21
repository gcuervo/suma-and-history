package com.tenpo.challenge.model.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class ApiCallHistoryMessage implements Serializable {

  private OffsetDateTime createdDate;
  private String requestPath;
  private String responseBody;
  private Integer httpStatusCode;
  private String errorMessage;

  public ApiCallHistoryMessage() {
  }

  public ApiCallHistoryMessage(
      String requestPath,
      Integer httpStatusCode
  ) {
    this.createdDate = OffsetDateTime.now();
    this.requestPath = requestPath;
    this.httpStatusCode = httpStatusCode;
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

  public void setCreatedDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public void setRequestPath(String requestPath) {
    this.requestPath = requestPath;
  }

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }

  public void setHttpStatusCode(Integer httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
