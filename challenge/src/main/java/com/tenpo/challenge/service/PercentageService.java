package com.tenpo.challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.model.dto.VariationDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class PercentageService {

  private static final Logger logger = LoggerFactory.getLogger(PercentageService.class);

  private final CacheManager cacheManager;
  private final ObjectMapper mapper;

  public PercentageService(
      CacheManager cacheManager,
      ObjectMapper mapper
  ) {
    this.cacheManager = cacheManager;
    this.mapper = mapper;
  }


  public Optional<BigDecimal> getPercentage() {
    return Optional.ofNullable(cacheManager.getCache("lastPercentage"))
        .map(c -> c.get("staticKey"))
        .map(this::getVariationPercentage)
        .map(VariationDto::getPercentage)
        .map(this::getPercentageFraction);
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
