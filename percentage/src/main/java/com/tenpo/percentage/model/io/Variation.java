package com.tenpo.percentage.model.io;

import java.math.BigDecimal;

public class Variation {

  private BigDecimal percentage;

  public Variation(BigDecimal percentage) {
    this.percentage = percentage;
  }

  public BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(BigDecimal percentage) {
    this.percentage = percentage;
  }
}
