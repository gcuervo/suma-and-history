package com.tenpo.challenge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TwoDigitOperationRequest {

  @NotNull(message = "num1 cannot be null")
  @Schema(description = "First number of the operation", example = "1.0")
  private BigDecimal num1;

  @NotNull(message = "num2 cannot be null")
  @Schema(description = "Second number of the operation", example = "2.0")
  private BigDecimal num2;


  public TwoDigitOperationRequest() {
  }


  public BigDecimal getNum1() {
    return num1;
  }

  public void setNum1(BigDecimal num1) {
    this.num1 = num1;
  }


  public BigDecimal getNum2() {
    return num2;
  }

  public void setNum2(BigDecimal num2) {
    this.num2 = num2;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("num1", num1)
        .append("num2", num2)
        .toString();
  }
}
