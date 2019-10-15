package com.redhat.techexchange.whosaidit.domain;

import javax.json.bind.annotation.JsonbProperty;

public class NewQuoteEvent extends BaseEvent implements Event{

  Quote quote;

  public NewQuoteEvent() {
    super();
  }

  public NewQuoteEvent(Quote quote) {
    this.eventType = EventType.NEW_QUOTE;
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

