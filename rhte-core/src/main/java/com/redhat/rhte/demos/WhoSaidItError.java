package com.redhat.rhte.demos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WhoSaidItError {

  @JsonProperty("message")
  public String message;

  public WhoSaidItError(String message) {
    this.message = message;
  }
}
