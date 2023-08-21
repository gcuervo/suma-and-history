package com.tenpo.challenge.model.entity;

import com.tenpo.challenge.model.dto.ApiCallHistoryMessage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(
    name = "api_call_history",
    indexes = {@Index(name = "index_created_date", columnList = "created_date")}
)
public class ApiCallHistory {

  @Id
  @GeneratedValue(
      strategy = GenerationType.IDENTITY
  )
  private Long id;

  @Column(name = "created_date", nullable = false)
  private OffsetDateTime createdDate;

  @Column(name = "request_path", nullable = false)
  private String requestPath;

  @Column(name = "response_body")
  private String responseBody;

  @Column(name = "http_status_code", nullable = false)
  private Integer httpStatusCode;

  @Column(name = "error_message", length = 555)
  private String errorMessage;

  ApiCallHistory() {
    // Required by JPA
  }

  public ApiCallHistory(String requestPath, Integer httpStatusCode) {
    this.createdDate = OffsetDateTime.now();
    this.requestPath = Objects.requireNonNull(requestPath);
    this.httpStatusCode = Objects.requireNonNull(httpStatusCode);
  }

  public ApiCallHistory(ApiCallHistoryMessage message) {
    this.createdDate = message.getCreatedDate();
    this.requestPath = message.getRequestPath();
    this.responseBody = message.getResponseBody();
    this.httpStatusCode = message.getHttpStatusCode();
    this.errorMessage = message.getErrorMessage();
  }

  public OffsetDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(OffsetDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getRequestPath() {
    return requestPath;
  }

  public void setRequestPath(String requestPath) {
    this.requestPath = requestPath;
  }

  public String getResponseBody() {
    return responseBody;
  }

  public void setResponseBody(String responseBody) {
    this.responseBody = responseBody;
  }

  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
