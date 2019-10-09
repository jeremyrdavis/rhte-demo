package com.redhat.techexchange.whosaidit.domain;

public class GuessReceivedEvent extends Event {

  public GuessReceivedEvent() {
    super(EventType.NEW_GUESS);
  }
}


