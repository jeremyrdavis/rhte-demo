package com.redhat.techexchange.whosaidit.domain;

public class RoundStartedEvent extends Event {


  public RoundStartedEvent() {
    super(EventType.ROUND_STARTED);
  }
}
