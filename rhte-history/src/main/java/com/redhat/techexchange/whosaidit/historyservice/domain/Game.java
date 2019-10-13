package com.redhat.techexchange.whosaidit.historyservice.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Game {

  @Id
  public String id;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "game_id")
  Map<Integer, Round> rounds = new HashMap<>();

  public Map<Integer, Round> getRounds() {
    return this.rounds;
  }

  public void addRound(Round round) {
    this.rounds.put(this.rounds.size() + 1, round);
  }

  public void completeRound(Integer currentRound) {
    this.rounds.get(currentRound).status = RoundStatus.COMPLETED;
  }
}
