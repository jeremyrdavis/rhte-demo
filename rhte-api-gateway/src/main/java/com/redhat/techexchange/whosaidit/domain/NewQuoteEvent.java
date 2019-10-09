package com.redhat.techexchange.whosaidit.domain;

public class NewQuoteEvent extends Event {

  public NewQuoteEvent() {
    super(EventType.NEW_QUOTE);
  }
}

