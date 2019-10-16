package com.redhat.techexchange.whosaidit.domain;

public class NextQuoteEvent implements Event{

  EventType eventType;

  Quote quote;

  public NextQuoteEvent() {
    this.eventType = EventType.NextQuoteEvent;
  }

  public NextQuoteEvent(EventType eventType, Quote quote) {
    this.eventType = EventType.NextQuoteEvent;
    this.quote = (Quote) quote;
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

  public String getEventType() {
    return this.eventType.title;
  }
}

