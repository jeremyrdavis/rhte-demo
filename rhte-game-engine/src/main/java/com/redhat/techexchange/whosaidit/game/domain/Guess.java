package com.redhat.techexchange.whosaidit.game.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Guess extends PanacheEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "round_id")
  public Round round;

  String player;

}
