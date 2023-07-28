package com.tenpo.challenge.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OperationResponse implements Serializable {

  private BigDecimal response;

  OperationResponse() {

  }

  public OperationResponse(BigDecimal response) {
    this.response = response;
  }

  public BigDecimal getResponse() {
    return response;
  }

  public void setResponse(BigDecimal response) {
    response = response;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("response", response)
        .toString();
  }
}
