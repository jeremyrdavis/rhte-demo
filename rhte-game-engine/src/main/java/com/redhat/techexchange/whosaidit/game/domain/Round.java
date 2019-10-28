package com.redhat.techexchange.whosaidit.game.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "round")
public class Round extends PanacheEntity {

  @JsonbTransient
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "game_id")
  public Game game;

  @Enumerated(EnumType.STRING)
  public RoundStatus status;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(name = "round_quotes",
    joinColumns = {@JoinColumn(name = "fk_round")},
    inverseJoinColumns = {@JoinColumn(name = "fk_quote")})
  public Map<Integer, Quote> quotes = new HashMap<>();


  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(name = "round_guesses",
    joinColumns = {@JoinColumn(name = "fk_round")},
    inverseJoinColumns = {@JoinColumn(name = "fk_guess")})
  public List<Guess> guesses;

  public String winner;

  public void addQuote(Quote quote) {
    this.quotes.put(this.quotes.size() + 1, quote);
  }

  public Map<Integer, Quote> getQuotes() {
    return this.quotes;
  }

}
