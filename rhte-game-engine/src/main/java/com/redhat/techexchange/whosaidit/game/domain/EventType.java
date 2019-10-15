package com.redhat.techexchange.whosaidit.game.domain;

public enum EventType {

  NextQuoteEvent("NextQuoteEvent"),
  GameStartedEvent("GameStartedEvent"),
  RoundStartedEvent("RoundStartedEvent"),
  RoundEndedEvent("RoundEndedEvent");

  public String title;

  private EventType(String titleToSet) {
    this.title = titleToSet;
  }

  public enum RoundEndedEvent {}
}
