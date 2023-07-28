package com.tenpo.challenge.resource;

import com.tenpo.challenge.model.dto.ApiCallHistoryDto;
import com.tenpo.challenge.model.dto.ApiCallHistoryRequest;
import com.tenpo.challenge.service.ApiCallHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenge/call-history")
@Tag(
    name = "Api call history",
    description = "Api call history of operations inside /api/challenge/"
)
public class ApiCallHistoryResource {

  private final ApiCallHistoryService service;

  public ApiCallHistoryResource(ApiCallHistoryService service) {
    this.service = service;
  }

  @GetMapping
  public Page<ApiCallHistoryDto> findAllApiCallHistory(@Valid ApiCallHistoryRequest page) {
    return service.findAllApiCallHistory(page);
  }
}
