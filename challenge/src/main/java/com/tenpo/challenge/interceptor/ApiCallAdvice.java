package com.tenpo.challenge.interceptor;

import com.tenpo.challenge.resource.ApiCallHistoryResource;
import com.tenpo.challenge.service.ApiCallHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ApiCallAdvice implements ResponseBodyAdvice<Object> {

  private final ApiCallHistoryService service;

  public ApiCallAdvice(ApiCallHistoryService service) {
    this.service = service;
  }

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (Objects.isNull(sra)) {
      return false;
    }
    return (sra.getRequest().getRequestURI().startsWith("/api/challenge/")
        || sra.getRequest().getRequestURI().startsWith("/error"))
        && !returnType.getContainingClass().equals(ApiCallHistoryResource.class);
  }

  @Override
  public Object beforeBodyWrite(
      Object body, MethodParameter returnType,
      MediaType selectedContentType, Class selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response
  ) {
    HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
    HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
    var requestUri = getRequestUriIfException(servletRequest)
        .orElse(addRequestParam(servletRequest.getRequestURI(), servletRequest));
    service.sendApiCallHistoryMessage(body, requestUri, servletResponse.getStatus());
    return body;
  }

  private Optional<String> getRequestUriIfException(HttpServletRequest request) {
    return Optional.ofNullable(
            (String) request.getAttribute("jakarta.servlet.forward.servlet_path"))
        .map(path -> addRequestParam(path, request));
  }

  private String addRequestParam(String requestUri, HttpServletRequest request) {
    if (request.getQueryString() == null) {
      return requestUri;
    }
    return requestUri.concat("?").concat(request.getQueryString());
  }
}
