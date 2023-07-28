package com.tenpo.percentage.resource;

import com.tenpo.percentage.model.io.Variation;
import com.tenpo.percentage.service.PercentageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/percentage")
public class PercentageResource {

  private final PercentageService service;

  public PercentageResource(PercentageService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Variation> getPercentageVariation() {
    return ResponseEntity.ok(service.getPercentage());
  }
}
