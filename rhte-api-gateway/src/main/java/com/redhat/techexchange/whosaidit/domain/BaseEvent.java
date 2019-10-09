package com.redhat.techexchange.whosaidit.domain;

public abstract class BaseEvent implements Event{

  EventType eventType;

  public BaseEvent(EventType eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return this.eventType.title;
  }
}
