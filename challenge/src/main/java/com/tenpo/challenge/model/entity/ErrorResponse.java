package com.tenpo.challenge.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

  private String error;
  private String message;

  public ErrorResponse() {
  }

  public ErrorResponse(String error, String message) {
    this.error = error;
    this.message = message;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("error", error)
        .append("message", message)
        .toString();
  }
}
