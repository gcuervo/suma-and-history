package com.tenpo.challenge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.model.dto.VariationDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

public class PercentageUtil {

  private static final Logger logger = LoggerFactory.getLogger(PercentageUtil.class);

  private static final ObjectMapper mapper = new ObjectMapper();

  public static VariationDto getVariationPercentage(Cache.ValueWrapper c) {
    try {
      return mapper.readValue(c.get().toString(), VariationDto.class);
    } catch (NullPointerException | JsonProcessingException e) {
      logger.error("Error al deserializar el porcentaje de variación", e);
      return null;
    }
  }

  public static BigDecimal getPercentageFraction(BigDecimal percentage) {
    return percentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
  }

}
