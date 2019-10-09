package com.redhat.techexchange.whosaidit.domain;

public class Event {

  EventType eventType;

  public Event(EventType eventType) {
    this.eventType = eventType;
  }

  public String getEventType() {
    return this.eventType.title;
  }

}
