package com.redhat.techexchange.whosaidit.game.domain;

public enum RoundStatus {

  CREATED("created"), ACTIVE("active"), COMPLETED("completed");

  public final String name;

  RoundStatus(String name) {
    this.name = name;
  }
}
