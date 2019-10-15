package com.redhat.techexchange.whosaidit.domain;

public enum EventType {

  GameStartedEvent("GameStartedEvent"),
  GameEndedEvent("GameEndedEvent"),
  ROUND_STARTED("RoundStarted"),
  RoundEndedEvent("RoundEndedEvent"),
  NextQuoteEvent("NextQuoteEvent"),
  GuessReceivedEvent("GuessReceivedEvent"),
  RoundStartedEvent("RoundStartedEvent");

  public String title;

  private EventType(String titleToSet) {
    this.title = titleToSet;
  }

}
