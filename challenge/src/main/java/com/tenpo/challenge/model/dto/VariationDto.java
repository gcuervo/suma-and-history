package com.tenpo.challenge.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class VariationDto implements Serializable {

  private BigDecimal percentage;

  public VariationDto() {
  }

  public VariationDto(BigDecimal percentage) {
    this.percentage = percentage;
  }

  public BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(BigDecimal percentage) {
    this.percentage = percentage;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("percentage", percentage)
        .toString();
  }
}
