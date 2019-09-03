package com.redhat.rhte.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Round extends PanacheEntity {

  @Transient
  public RoundStatus status;

  @OneToMany
  @JoinColumn(name = "quote_id")
  public Set<Quote> quotes;

  @OneToMany
  @JoinColumn(name = "round_id")
  public Set<Guess> guesses;

  public Round() {

    this.guesses = new HashSet<Guess>();
    this.quotes = new HashSet<Quote>();
  }

  public void start() {

    this.status = RoundStatus.ACTIVE;
  }

  public void stop() {

    this.status = RoundStatus.COMPLETED;
  }

  public boolean addQuote(Quote quote) {

    return this.quotes.add(quote);
  }

  public void addGuess(Guess guess) {

    this.guesses.add(guess);
  }

  public Set<Quote> getQuotes() {

    return this.quotes;
  }

  public enum RoundStatus {

    ACTIVE, COMPLETED;
  }

}
