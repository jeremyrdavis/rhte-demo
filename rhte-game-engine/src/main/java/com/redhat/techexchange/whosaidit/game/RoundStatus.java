package com.redhat.techexchange.whosaidit.game;

public enum RoundStatus {

  ACTIVE("active"), COMPLETED("completed");

  public final String name;

  RoundStatus(String name) {
    this.name = name;
  }
}
