package com.redhat.rhte.demos.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoundStatus {

  ACTIVE("active"), COMPLETED("completed");

  @JsonValue
  public final String name;

  RoundStatus(String name) {
    this.name = name;
  }
}
