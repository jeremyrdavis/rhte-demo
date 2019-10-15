package com.redhat.techexchange.whosaidit.historyservice.domain;

public enum EntityType {

  Game("Game"),
  Round("Round"),
  Quote("Quote");

  public String title;

  private EntityType(String titleToSet) {
    this.title = titleToSet;
  }

}
