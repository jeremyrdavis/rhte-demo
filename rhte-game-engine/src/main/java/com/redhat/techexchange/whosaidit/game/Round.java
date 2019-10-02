package com.redhat.techexchange.whosaidit.game;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Round extends PanacheEntity {

  @ManyToOne
  @JoinColumn(name = "game_id")
  public Game game;

  public RoundStatus status;

  @ManyToMany
  @JoinTable(name = "round_quotes",
    joinColumns = {@JoinColumn(name = "fk_round")},
    inverseJoinColumns = {@JoinColumn(name = "fk_quote")})
  Map<Integer, Quote> quotes = new HashMap<>();

  public void addQuote(Quote quote) {
    this.quotes.put(this.quotes.size() + 1, quote);
  }

}
