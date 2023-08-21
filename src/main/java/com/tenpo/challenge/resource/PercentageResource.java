package com.tenpo.challenge.resource;

import com.tenpo.challenge.service.PercentageService;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenge/last-percentage")
public class PercentageResource {

  private final PercentageService service;

  public PercentageResource(PercentageService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<BigDecimal> getLastPercentage() {
    return service.getPercentage()
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
