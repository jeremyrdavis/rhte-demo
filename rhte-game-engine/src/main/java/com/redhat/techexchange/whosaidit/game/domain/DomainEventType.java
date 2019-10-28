package com.redhat.techexchange.whosaidit.game.domain;

public enum DomainEventType {

  GameCreatedEvent("GameCreatedEvent"),
  GameStartedEvent("GameStartedEvent"),
  NextQuoteEvent("NextQuoteEvent"),
  RoundStartedEvent("RoundStartedEvent"),
  RoundEndedEvent("RoundEndedEvent");

  public String title;

  private DomainEventType(String titleToSet) {
    this.title = titleToSet;
  }

}
