package com.backend.portfolio.DTO.bodyResponse;

public class apiResponse<T> {

  public String message;

  private T payload;

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
