package com.redhat.techexchange.whosaidit.game.domain;

import com.redhat.techexchange.whosaidit.game.domain.Event;
import com.redhat.techexchange.whosaidit.game.domain.EventType;
import com.redhat.techexchange.whosaidit.game.domain.Round;

public class RoundEndedEvent implements Event {

  EventType eventType = EventType.RoundEndedEvent;

  Round round;

  public RoundEndedEvent() {
  }

  public RoundEndedEvent(EventType eventType, Round round) {
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
