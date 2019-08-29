package com.redhat.rhte.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Round extends PanacheEntity {

  @Transient
  public RoundStatus status;

  public void start() {
    this.status = RoundStatus.ACTIVE;
  }

  public void stop() {

    this.status = RoundStatus.COMPLETED;
  }

  public enum RoundStatus {

    ACTIVE, COMPLETED;
  }

}
