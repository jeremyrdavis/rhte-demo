package com.redhat.techexchange.whosaidit.domain;

public enum EventType {

  GAME_STARTED("gameStarted"), GAME_ENDED("gameEnded"), ROUND_STARTED("roundStarted"), ROUND_ENDED("roundEended"), NEW_QUOTE("newQuote"), NEW_GUESS("newGguess");

  public String title;

  private EventType(String titleToSet) {
    this.title = titleToSet;
  }

}
