package com.redhat.rhte.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbTransient;
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

  public Guess(){}

  public Guess(String contestant, String author, Round round, Quote quote) {
    this.contestant = contestant;
    this.author = author;
    this.round = round;
    this.quote = quote;
  }

  @JsonbTransient
  public boolean isCorrect() {

    return this.author.equals(this.quote.author.toString());
  }
}
