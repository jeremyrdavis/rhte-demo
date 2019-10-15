package com.redhat.techexchange.whosaidit.historyservice.domain;

public enum EventType {

  GameStartedEvent("gameStartedEvent"),
  RoundStartedEvent("roundStartedEvent"),
  RoundEndedEvent("roundEndedEvent"),
  NextQuoteEvent("nextQuoteEvent");

  public String title;

  private EventType(String titleToSet) {
    this.title = titleToSet;
  }
}
