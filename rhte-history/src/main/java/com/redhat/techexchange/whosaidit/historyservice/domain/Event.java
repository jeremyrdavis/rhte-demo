package com.redhat.techexchange.whosaidit.historyservice.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Event extends PanacheEntity {

  @Enumerated(EnumType.STRING)
  public EventType eventType;

  public Date timestamp;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "game_id")
  public Game game;

  public Event() {
  }

  @JsonbProperty("event_type")
  public EventType getEventType() {
    return eventType;
  }

  @JsonbProperty("timestamp")
  public Date getTimestamp() {
    return timestamp;
  }

  public Game getGame() {
    return game;
  }
}
