package com.redhat.techexchange.whosaidit.game;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

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
  Set<Quote> quotes = new HashSet<>();

}
