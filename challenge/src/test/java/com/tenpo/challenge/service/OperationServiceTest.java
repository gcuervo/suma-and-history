package com.tenpo.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.tenpo.challenge.model.dto.TwoDigitOperationRequest;
import com.tenpo.challenge.model.entity.OperationType;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {

  @Mock
  private TwoNumOperationStrategy twoNumOperationStrategy;

  @Mock
  private ExternalService externalService;

  @Mock
  private SumPlusTaxOperation sumPlusTaxOperation;

  @InjectMocks
  private OperationService underTest;

  @Test
  void givenTwoDigitAndTenPercentageWhenExecuteSumOperationThenReturnTwoDigitsSumWithPercentage() {
    var request = new TwoDigitOperationRequest();
    request.setNum1(BigDecimal.valueOf(5));
    request.setNum2(BigDecimal.valueOf(5));
    var num1 = BigDecimal.valueOf(5);
    var num2 = BigDecimal.valueOf(5);
    // Given
    given(twoNumOperationStrategy.getTwoNumBasicOpStrategy(OperationType.SUM_PLUS_TAX)).willReturn(Optional.of(
        sumPlusTaxOperation));
    given(sumPlusTaxOperation.execute(num1, num2))
        .willReturn(Optional.of(BigDecimal.valueOf(11)));
    // When
    var response = underTest.executeSumOperation(num1, num2);
    // Then
    assertEquals(response.getResponse(), BigDecimal.valueOf(11));
  }

  @Test
  void givenTwoDigitsAndNoSumValueWhenExecuteSumOperationThenReturnException() {
    var request = new TwoDigitOperationRequest();
    request.setNum1(BigDecimal.valueOf(5));
    request.setNum2(BigDecimal.valueOf(5));
    var num1 = BigDecimal.valueOf(5);
    var num2 = BigDecimal.valueOf(5);
    // Given
    given(twoNumOperationStrategy.getTwoNumBasicOpStrategy(OperationType.SUM_PLUS_TAX)).willReturn(Optional.of(
        sumPlusTaxOperation));
    given(sumPlusTaxOperation.execute(num1, num2))
        .willReturn(Optional.empty());
    // Then
    assertThrows(RuntimeException.class, () -> underTest.executeSumOperation(num1, num2));
  }

  //TODO: testear si el operationStrategy retorna vacio
}
