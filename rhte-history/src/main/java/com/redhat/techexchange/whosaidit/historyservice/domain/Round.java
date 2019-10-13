package com.redhat.techexchange.whosaidit.historyservice.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Round {

  @Id
  public String id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "game_id")
  public Game game;

  public RoundStatus status;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(name = "round_quotes",
    joinColumns = {@JoinColumn(name = "fk_round")},
    inverseJoinColumns = {@JoinColumn(name = "fk_quote")})
  Map<Integer, Quote> quotes = new HashMap<>();

  public void addQuote(Quote quote) {
    this.quotes.put(this.quotes.size() + 1, quote);
  }

  public Map<Integer, Quote> getQuotes() {
    return this.quotes;
  }
}
