package com.redhat.techexchange.whosaidit.domain;

public enum EventType {

  GAME_STARTED("GameStartedEvent"),
  GAME_ENDED("GameEndedEvent"),
  ROUND_STARTED("RoundStarted"),
  ROUND_ENDED("RoundEnded"),
  NEW_QUOTE("NextQuoteEvent"),
  NEW_GUESS("GuessReceivedEvent");

  public String title;

  private EventType(String titleToSet) {
    this.title = titleToSet;
  }

}
