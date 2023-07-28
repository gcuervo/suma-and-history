package com.tenpo.challenge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class ApiCallHistoryRequest {

  @DecimalMin(value = "0", message = "Page number must not be less than zero!")
  private int page;

  @DecimalMin(value = "1", message = "Page size must not be less than one!")
  @DecimalMax(value = "100", message = "Page size must not be greater than one hundred!")
  @Schema(description = "Maximum number of elements per page", example = "5")
  private int size;

  @Nullable
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime startDate;

  @Nullable
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime endDate;

  ApiCallHistoryRequest() {
    this.page = 0;
    this.size = 5;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Nullable
  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(@Nullable OffsetDateTime startDate) {
    this.startDate = startDate;
  }

  @Nullable
  public OffsetDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(@Nullable OffsetDateTime endDate) {
    this.endDate = endDate;
  }
}
