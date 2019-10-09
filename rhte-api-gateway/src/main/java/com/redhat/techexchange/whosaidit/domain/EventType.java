package com.redhat.techexchange.whosaidit.domain;

public enum EventType {

  GAME_STARTED("game started"), GAME_ENDED("game ended"), ROUND_STARTED("round started"), ROUND_ENDED("round ended"), NEW_QUOTE("new quote"), NEW_GUESS("new guess");

  public String title;

  private EventType(String titleToSet) {
    this.title = titleToSet;
  }

}
