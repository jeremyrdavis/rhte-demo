package com.redhat.techexchange.whosaidit.historyservice.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class GameStartedEvent extends PanacheEntity {

  static final EventType eventType = EventType.GameStartedEvent;

  Date timestamp;

  public GameStartedEvent(Date timestamp) {
    this.timestamp = timestamp;
  }

  public GameStartedEvent() {
  }

  public EventType getEventType() {
    return eventType;
  }

  public Date getTimestamp() {
    return timestamp;
  }
}
