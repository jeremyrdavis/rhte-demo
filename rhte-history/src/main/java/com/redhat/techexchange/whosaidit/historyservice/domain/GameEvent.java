package com.redhat.techexchange.whosaidit.historyservice.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class GameEvent extends PanacheEntity {

  @Enumerated(EnumType.STRING)
  EventType eventType;

  @Enumerated(EnumType.STRING)
  EntityType entityType;

  @Column(name = "data", length = 10000)
  String data;
}
