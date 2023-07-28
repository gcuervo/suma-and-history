package com.tenpo.challenge.resource;

import com.tenpo.challenge.model.dto.OperationResponse;
import com.tenpo.challenge.model.dto.TwoDigitOperationRequest;
import com.tenpo.challenge.service.OperationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenge/operation")
public class OperationResource {

  private final OperationService service;

  public OperationResource(OperationService service) {
    this.service = service;
  }

  @GetMapping("/sum-plus-tax")
  public OperationResponse sumPlusTax(
      @Valid TwoDigitOperationRequest request
  ) {
    return service.executeSumOperation(request.getNum1(), request.getNum2());
  }
}
