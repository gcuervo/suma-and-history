package com.tenpo.challenge.service;

import com.tenpo.challenge.model.entity.OperationType;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SumPlusTaxOperation implements TwoNumbersOperation {

  private final ExternalService externalService;

  public SumPlusTaxOperation(ExternalService externalService) {
    this.externalService = externalService;
  }

  @Override
  @Cacheable(key = "#num1.toString().concat('-').concat(#num2.toString())", cacheNames = "sum")
  public Optional<BigDecimal> execute(@NonNull BigDecimal num1, @NonNull BigDecimal num2) {
    var sum = num1.add(num2);
    return externalService.getPercentage()
        .map(sum::multiply)
        .map(sum::add);
  }

  @Override
  public OperationType getOperationType() {
    return OperationType.SUM_PLUS_TAX;
  }
}
