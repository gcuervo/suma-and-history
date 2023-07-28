package com.tenpo.challenge.gateway;

import com.tenpo.challenge.model.dto.VariationDto;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ExternalServiceGateway {

  @Value("${percentage-api.base-path}")
  private String percentageApiBasePath;
  @Value("${percentage-api.percentage-path}")
  private String percentagePath;

  private final WebClient.Builder webClientBuilder;

  public ExternalServiceGateway(WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  @CachePut(value = "lastPercentage", key = "'staticKey'")
  public Optional<VariationDto> getIncreasePercentage() {
    return webClientBuilder.build()
        .get()
        .uri(percentageApiBasePath + percentagePath)
        .retrieve()
        .bodyToMono(VariationDto.class)
        .blockOptional();
  }
}
