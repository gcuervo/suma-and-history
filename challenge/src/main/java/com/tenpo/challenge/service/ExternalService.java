package com.tenpo.challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.exception.ExternalServiceException;
import com.tenpo.challenge.gateway.ExternalServiceGateway;
import com.tenpo.challenge.model.dto.VariationDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class ExternalService {

  private static final Logger logger = LoggerFactory.getLogger(ExternalService.class);

  private final ExternalServiceGateway externalServiceGateway;
  private final CacheManager cacheManager;
  private final ObjectMapper mapper;

  public ExternalService(
      ExternalServiceGateway externalServiceGateway,
      CacheManager cacheManager,
      ObjectMapper mapper
  ) {
    this.externalServiceGateway = externalServiceGateway;
    this.cacheManager = cacheManager;
    this.mapper = mapper;
  }

  @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000), retryFor = RuntimeException.class)
  public Optional<BigDecimal> getPercentage() {
    return getIncreasePercentage()
        .map(this::getPercentageFraction);
  }

  @Recover
  public Optional<BigDecimal> recoverGetPercentage(RuntimeException e) {
    var cache = cacheManager.getCache("lastPercentage");
    var percentage = Optional.ofNullable(cache)
        .map(c -> c.get("staticKey"))
        .map(this::getVariationPercentage)
        .map(VariationDto::getPercentage)
        .map(this::getPercentageFraction);
    if (percentage.isEmpty()) {
      logger.error("No se pudo obtener el porcentaje de variación", e);
      throw new ExternalServiceException("No se pudo obtener el porcentaje de variación");
    }
    return percentage;
  }

  private Optional<BigDecimal> getIncreasePercentage() {
    return externalServiceGateway.getIncreasePercentage()
        .map(VariationDto::getPercentage);
  }

  public VariationDto getVariationPercentage(Cache.ValueWrapper c) {
    try {
      return mapper.readValue(c.get().toString(), VariationDto.class);
    } catch (NullPointerException | JsonProcessingException e) {
      logger.error("Error al deserializar el porcentaje de variación", e);
      return null;
    }
  }

  public BigDecimal getPercentageFraction(BigDecimal percentage) {
    return percentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
  }
}
