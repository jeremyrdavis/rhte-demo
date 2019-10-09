package com.redhat.techexchange.whosaidit.domain;

public class GuessReceivedEvent extends BaseEvent {

  public GuessReceivedEvent() {
    super(EventType.NEW_GUESS);
  }
}


