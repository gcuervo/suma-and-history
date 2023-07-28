package com.tenpo.challenge.exception;

public class ExternalServiceException extends RuntimeException {
  public ExternalServiceException(String message) {
    super(message);
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this; // Retornar la instancia actual sin llenar el stack trace
  }
}
