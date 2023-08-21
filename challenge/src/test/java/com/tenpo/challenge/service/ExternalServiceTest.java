package com.tenpo.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.gateway.ExternalServiceGateway;
import com.tenpo.challenge.model.dto.VariationDto;
import com.tenpo.challenge.util.PercentageUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@ExtendWith(MockitoExtension.class)
class ExternalServiceTest {

  @Mock
  private ExternalServiceGateway externalServiceGateway;

  @Mock
  private CacheManager cacheManager;

  @Mock
  private ObjectMapper mapper;

  @InjectMocks
  private ExternalService underTest;

  @Test
  void whenGetPercentageThenReturnPercentageFraction() {
    var variation = new VariationDto();
    variation.setPercentage(BigDecimal.TEN);
    // given
    given(externalServiceGateway.getIncreasePercentage()).willReturn(Optional.of(variation));
    // when
    var result = underTest.getPercentage();
    // then
    assertEquals(
        BigDecimal.TEN.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP),
        result.get()
    );
  }

  @Test
  void whenGetPercentageAndExternalServiceIsNotAvailableThenReturnLastPercentage() {
    var cache = mock(Cache.class);
    var cacheWrapper = mock(Cache.ValueWrapper.class);
    var variation = new VariationDto();
    variation.setPercentage(BigDecimal.TEN);
    // given
    given(cacheManager.getCache("lastPercentage")).willReturn(cache);
    given(cache.get("staticKey")).willReturn(cacheWrapper);
    try (MockedStatic<PercentageUtil> mocked = mockStatic(PercentageUtil.class)) {
      mocked.when(() -> PercentageUtil.getPercentageFraction(BigDecimal.TEN))
          .thenReturn(BigDecimal.TEN.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
      mocked.when(() -> PercentageUtil.getVariationPercentage(any()))
          .thenReturn(variation);
      // when
      var result = underTest.recoverGetPercentage(new RuntimeException());
      // then
      assertEquals(
          BigDecimal.TEN.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP),
          result.get()
      );
    }
  }

  @Test
  void whenGetPercentageAndExternalServiceIsNotAvailableThenReturnException() {
    // given
    given(cacheManager.getCache("lastPercentage")).willReturn(null);
    // when
    Executable executable = () -> underTest.recoverGetPercentage(new RuntimeException());
    // then
    assertThrows(RuntimeException.class, executable);
  }
}
