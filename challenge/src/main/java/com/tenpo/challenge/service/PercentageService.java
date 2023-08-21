package com.tenpo.challenge.service;

import com.tenpo.challenge.model.dto.VariationDto;
import com.tenpo.challenge.util.PercentageUtil;
import java.math.BigDecimal;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class PercentageService {

  private static final Logger logger = LoggerFactory.getLogger(PercentageService.class);

  private final CacheManager cacheManager;

  public PercentageService(
      CacheManager cacheManager
  ) {
    this.cacheManager = cacheManager;
  }

  public Optional<BigDecimal> getPercentage() {
    return Optional.ofNullable(cacheManager.getCache("lastPercentage"))
        .map(c -> c.get("staticKey"))
        .map(PercentageUtil::getVariationPercentage)
        .map(VariationDto::getPercentage)
        .map(PercentageUtil::getPercentageFraction);
  }
}
