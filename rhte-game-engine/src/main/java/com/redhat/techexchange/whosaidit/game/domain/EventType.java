package com.redhat.techexchange.whosaidit.game.domain;

public enum EventType {

  NextQuoteEvent("NextQuoteEvent"), GameStartedEvent("GameStartedEvent");

  public String title;

  private EventType(String titleToSet) {
    this.title = titleToSet;
  }
}
