package com.tenpo.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SumPlusTaxOperationTest {

  @Mock
  private ExternalService externalService;

  @InjectMocks
  private SumPlusTaxOperation underTest;

  @Test
  void givenTwoDigitAndTenPercentageWhenExecuteSumOperationThenReturnTwoDigitsSumWithPercentage() {
    // given
    given(externalService.getPercentage()).willReturn(Optional.of(BigDecimal.valueOf(0.10)));
    // when
    var result = underTest.execute(BigDecimal.valueOf(5), BigDecimal.valueOf(5));
    // then
    assertEquals(BigDecimal.valueOf(11.0), result.get());
  }

  @Test
  void givenOneDigitWhenExecuteSumOperationThenShouldThrowException() {
    // when
    Executable executable = () -> underTest.execute(BigDecimal.valueOf(5), null);
    // then
    assertThrows(NullPointerException.class, executable);
  }

  @Test
  void givenTwoDigitAndNoPercentageWhenExecuteSumOperationThenShouldThrowException() {
    // given
    given(externalService.getPercentage()).willThrow(new RuntimeException());
    // when
    Executable executable = () -> underTest.execute(BigDecimal.valueOf(5), BigDecimal.valueOf(5));
    // then
    assertThrows(RuntimeException.class, executable);
  }
}
