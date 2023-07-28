package com.tenpo.challenge.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.tenpo.challenge.model.entity.OperationType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TwoNumOperationStrategyTest {


  @Test
  void givenValidOperationTypeWhenGetTwoNumBasicOpStrategyThenReturnTwoNumBasicOpStrategy() {
    var operation = mock(SumPlusTaxOperation.class);
    var operationType = OperationType.SUM_PLUS_TAX;
    // Given
    given(operation.getOperationType()).willReturn(operationType);
    List<TwoNumbersOperation> operations = List.of(operation);
    TwoNumOperationStrategy underTest = new TwoNumOperationStrategy(operations);
    // When
    var operationResponse = underTest.getTwoNumBasicOpStrategy(operationType);

    // Then
    assertSame(operation, operationResponse.get());
  }

  @Test
  void givenInvalidOperationTypeWhenGetTwoNumBasicOpStrategyThenReturnEmptyOptional() {
    var operation = mock(SumPlusTaxOperation.class);
    var operationType = OperationType.SUM_PLUS_TAX;
    // Given
    given(operation.getOperationType()).willReturn(null);
    List<TwoNumbersOperation> operations = List.of(operation);
    TwoNumOperationStrategy underTest = new TwoNumOperationStrategy(operations);
    // When
    var operationResponse = underTest.getTwoNumBasicOpStrategy(operationType);
    // Then
    assertTrue(operationResponse.isEmpty());
  }
}
