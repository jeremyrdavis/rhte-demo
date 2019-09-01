package com.redhat.rhte.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@Entity
public class Guess extends PanacheEntity {

  public String contestant;
  public String author;

  @ManyToOne
  @JoinColumn(name = "round_id")
  public Round round;

  @ManyToOne
  @JoinColumn(name = "quote_id")
  public Quote quote;

}
