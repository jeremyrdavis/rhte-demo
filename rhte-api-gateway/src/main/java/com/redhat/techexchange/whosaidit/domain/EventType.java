package com.redhat.techexchange.whosaidit.domain;

public enum EventType {

  GameStartedEvent("GameStartedEvent"),
  GAME_ENDED("GameEndedEvent"),
  ROUND_STARTED("RoundStarted"),
  RoundEndedEvent("RoundEndedEvent"),
  NextQuoteEvent("NextQuoteEvent"),
  NEW_GUESS("GuessReceivedEvent"),
  RoundStartedEvent("RoundStartedEvent");

  public String title;

  private EventType(String titleToSet) {
    this.title = titleToSet;
  }

}
