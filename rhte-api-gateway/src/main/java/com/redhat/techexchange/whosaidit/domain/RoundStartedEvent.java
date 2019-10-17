package com.redhat.techexchange.whosaidit.domain;

public class RoundStartedEvent implements Event {

  EventType eventType = EventType.RoundEndedEvent;

  String winner;

  public RoundStartedEvent() {
  }

  public RoundStartedEvent(EventType eventType, String winner) {
    this.winner = winner;
  }

  public String getEventType() {
    return null;
  }

  public String getWinner() {
    return winner;
  }
}
