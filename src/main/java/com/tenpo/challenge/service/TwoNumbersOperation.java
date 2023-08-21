package com.tenpo.challenge.service;

import com.tenpo.challenge.model.entity.OperationType;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.lang.NonNull;

public interface TwoNumbersOperation {

  Optional<BigDecimal> execute(@NonNull BigDecimal num1, @NonNull BigDecimal num2);

  OperationType getOperationType();
}
