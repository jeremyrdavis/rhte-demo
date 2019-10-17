package com.redhat.techexchange.whosaidit.domain;

public class GameStartedEvent implements Event{

  EventType eventType = EventType.GameStartedEvent;

  public GameStartedEvent() {
  }

  public String getEventType() {
    return eventType.title;
  }

}



