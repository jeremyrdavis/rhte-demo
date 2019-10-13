package com.redhat.techexchange.whosaidit.historyservice.domain;

public enum EventType {

  GameStartedEvent("Game Started Event"), RoundStartedEvent("Round Started Event");

  public String title;

  private EventType(String titleToSet) {
    this.title = titleToSet;
  }
}
