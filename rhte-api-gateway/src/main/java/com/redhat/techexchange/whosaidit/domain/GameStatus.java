package com.redhat.techexchange.whosaidit.domain;

public enum GameStatus {


  STARTED("started"), COMPLETED("completed");

  public String title;

  private GameStatus(String title) {
    this.title = title;
  }
}
