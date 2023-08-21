package com.tenpo.challenge.service;

import com.tenpo.challenge.model.entity.OperationType;
import com.tenpo.challenge.model.dto.OperationResponse;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class OperationService {

  private final TwoNumOperationStrategy operationStrategy;

  public OperationService(TwoNumOperationStrategy operationStrategy) {
    this.operationStrategy = operationStrategy;
  }

  public OperationResponse executeSumOperation(BigDecimal num1, BigDecimal num2) {
    return operationStrategy.getTwoNumBasicOpStrategy(OperationType.SUM_PLUS_TAX)
        .flatMap(sumOperation -> sumOperation.execute(num1, num2))
        .map(OperationResponse::new)
        .orElseThrow(() -> new RuntimeException("Error executing operation"));
  }
}
