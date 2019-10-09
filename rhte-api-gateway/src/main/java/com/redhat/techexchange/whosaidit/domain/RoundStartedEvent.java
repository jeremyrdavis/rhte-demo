package com.redhat.techexchange.whosaidit.domain;

public class RoundStartedEvent extends BaseEvent {

  public RoundStartedEvent() {
    super(EventType.ROUND_STARTED);
  }
}
