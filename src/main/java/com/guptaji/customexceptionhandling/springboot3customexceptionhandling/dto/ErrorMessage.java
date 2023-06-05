package com.guptaji.customexceptionhandling.springboot3customexceptionhandling.dto;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

public class ErrorMessage {

  private String errorMessage;
  private HttpStatus errorStatus;
  private StackTraceElement[] errorTrace;

  public ErrorMessage() {
    // Default constructor
  }

  public ErrorMessage(String errorMessage, HttpStatus errorStatus, StackTraceElement[] errorTrace) {
    this.errorMessage = errorMessage;
    this.errorStatus = errorStatus;
    this.errorTrace = errorTrace;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public HttpStatus getErrorStatus() {
    return errorStatus;
  }

  public void setErrorStatus(HttpStatus errorStatus) {
    this.errorStatus = errorStatus;
  }

  public StackTraceElement[] getErrorTrace() {
    return errorTrace;
  }

  public void setErrorTrace(StackTraceElement[] errorTrace) {
    this.errorTrace = errorTrace;
  }

  @Override
  public String toString() {
    return "ErrorMessage{"
        + "errorMessage='"
        + errorMessage
        + '\''
        + ", errorStatus='"
        + errorStatus
        + '\''
        + ", errorTrace='"
        + Arrays.toString(errorTrace)
        + '\''
        + '}';
  }
}
