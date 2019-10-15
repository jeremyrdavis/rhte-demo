package com.redhat.techexchange.whosaidit.domain;

public class NextQuoteEvent extends BaseEvent implements Event{

  Quote quote;

  public NextQuoteEvent() {
    super();
  }

  public NextQuoteEvent(EventType eventType, Quote quote) {
    this.eventType = EventType.NextQuoteEvent;
    this.quote = (Quote) quote;
  }

  public Quote getQuote() {
    return this.quote;
  }

  @Override
  public String getEventType() {
    return this.eventType.title;
  }
}

