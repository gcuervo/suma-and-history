package com.tenpo.challenge.service;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.tenpo.challenge.model.entity.OperationType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TwoNumOperationStrategy {

  private final Map<OperationType, TwoNumbersOperation> operationMap;

  public TwoNumOperationStrategy(List<TwoNumbersOperation> twoNumbersOperationMap) {
    this.operationMap = twoNumbersOperationMap
        .stream()
        .collect(toMap(TwoNumbersOperation::getOperationType, identity()));
  }

  public Optional<TwoNumbersOperation> getTwoNumBasicOpStrategy(OperationType operationType) {
    return Optional.ofNullable(operationMap.get(operationType));
  }
}
