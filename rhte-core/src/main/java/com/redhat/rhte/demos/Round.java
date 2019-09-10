package com.redhat.rhte.demos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Round extends PanacheEntity {

  @JsonProperty("status")
  @Enumerated(EnumType.STRING)
  public RoundStatus status;

  @ManyToOne
  @JoinColumn(name = "game_id")
  @JsonIgnore
  public Game game;

  @ManyToMany(fetch = FetchType.EAGER)
  public Set<Quote> quotes;

  @OneToMany
  @JoinColumn(name = "round_id")
  @JsonManagedReference
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
