package com.tenpo.percentage.service;

import com.tenpo.percentage.model.io.Variation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class PercentageService {

  private static final BigDecimal MIN = BigDecimal.ZERO;
  private static final BigDecimal MAX = BigDecimal.valueOf(100);
  private static final int SCALE = 2;

  public Variation getPercentage() {
    return new Variation(getRandomBigDecimal(MIN, MAX));
  }

  public static BigDecimal getRandomBigDecimal(BigDecimal min, BigDecimal max) {
    Random random = new Random();
    BigDecimal randomBigDecimal = min.add(
        new BigDecimal(String.valueOf(random.nextDouble())).multiply(max.subtract(min))
    );
    return randomBigDecimal.setScale(SCALE, RoundingMode.HALF_UP);
  }
}
