package com.redhat.rhte.demos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Guess extends PanacheEntity {

  public String contestant;
  public String author;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "round_id")
  @JsonBackReference
  public Round round;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "quote_id")
  public Quote quote;

  public Guess(){}

  public Guess(String contestant, String author, Round round, Quote quote) {
    this.contestant = contestant;
    this.author = author;
    this.round = round;
    this.quote = quote;
  }

  @JsonIgnore
  public boolean isCorrect() {

    return this.author.equals(this.quote.author.toString());
  }
}
