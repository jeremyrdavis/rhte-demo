package com.redhat.techexchange.whosaidit.game.domain;

public class NextQuoteEvent implements Event{

  EventType eventType;

  Quote quote;

  public NextQuoteEvent() {
  }

  public NextQuoteEvent(EventType eventType, Quote quote) {
    this.eventType = eventType;
    this.quote = quote;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public Quote getQuote() {
    return quote;
  }

  public void setQuote(Quote quote) {
    this.quote = quote;
  }
}
