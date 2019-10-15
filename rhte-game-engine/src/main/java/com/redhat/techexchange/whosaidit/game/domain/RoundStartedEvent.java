package com.redhat.techexchange.whosaidit.game.domain;

public class RoundStartedEvent implements Event{

  EventType eventType;

  Round round;

  public RoundStartedEvent() {
  }

  public RoundStartedEvent(EventType eventType, Round round) {
    this.eventType = eventType;
    this.round = round;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public Round getRound() {
    return round;
  }

  public void setRound(Round round) {
    this.round = round;
  }
}
