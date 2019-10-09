package com.redhat.techexchange.whosaidit.domain;

import javax.json.bind.annotation.JsonbProperty;

public class NewQuoteEvent extends BaseEvent{

  Quote quote;

  public NewQuoteEvent(Quote quote) {

    super(EventType.NEW_QUOTE);
    this.quote = quote;
  }

  public Quote getQuote() {
    return this.quote;
  }

}

