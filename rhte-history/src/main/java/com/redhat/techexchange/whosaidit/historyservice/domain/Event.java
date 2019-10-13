package com.redhat.techexchange.whosaidit.historyservice.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Event extends PanacheEntity {

  @Enumerated(EnumType.STRING)
  EventType eventType;

  Date timestamp;

  @ManyToOne
  @JoinColumn(name = "game_id")
  Game game;

  public Event(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Event() {
  }

  public EventType getEventType() {
    return eventType;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public Game getGame() {
    return game;
  }
}
