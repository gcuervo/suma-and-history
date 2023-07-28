package com.tenpo.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.gateway.ExternalServiceGateway;
import com.tenpo.challenge.model.dto.VariationDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    assertEquals(BigDecimal.TEN.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP), result.get());
  }

  @Test
  void whenGetPercentageAndExternalServiceIsNotAvailableThenReturnLastPercentage() throws JsonProcessingException {
    var cache = mock(Cache.class);
    var cacheWrapper = mock(Cache.ValueWrapper.class);
    //var percentageUtil = mockStatic(PercentageUtil.class);
    var variation = new VariationDto();
    variation.setPercentage(BigDecimal.TEN);
    // given
    given(cacheManager.getCache("lastPercentage")).willReturn(cache);
    given(cache.get("staticKey")).willReturn(cacheWrapper);
    given(mapper.readValue(anyString(), eq(VariationDto.class))).willReturn(variation);
    given(cacheWrapper.get()).willReturn(BigDecimal.TEN);
    // when
    var result = underTest.recoverGetPercentage(new RuntimeException());
    // then
    assertEquals(Optional.of(BigDecimal.TEN.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)), result);
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
