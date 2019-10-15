package com.redhat.techexchange.whosaidit.historyservice.domain;

public enum EventType {

  GameStartedEvent("GameStartedEvent"),
  RoundStartedEvent("RoundStartedEvent"),
  RoundEndedEvent("RoundEndedEvent"),
  NextQuoteEvent("NextQuoteEvent");

  public String title;

  private EventType(String titleToSet) {
    this.title = titleToSet;
  }
}
