package com.demo.identity.exception;

public class VerificationFailedException extends Exception {
  public VerificationFailedException() {
  }

  public VerificationFailedException(String message) {
    super(message);
  }

  public VerificationFailedException(String message, Throwable cause) {
    super(message, cause);
  }
}
