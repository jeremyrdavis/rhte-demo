package com.redhat.techexchange.whosaidit.game;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class Game extends PanacheEntity {


  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  @Cascade(CascadeType.ALL)
  Map<Integer, Round> rounds = new HashMap<>();


  public void startRound(Integer currentRound) {
    this.rounds.get(currentRound).status = RoundStatus.ACTIVE;
  }

  public Round getCurrentRound() {

    for (Round r : this.rounds.values()) {
      if (r.status == RoundStatus.ACTIVE) {
        return r;
      }
    }
    this.rounds.get(1).status = RoundStatus.ACTIVE;
    return this.rounds.get(1);
  }

  public Quote nextQuote(int i) {

    return getCurrentRound().quotes.get(i);
  }

  public void addRound(Round round) {
    this.rounds.put(this.rounds.size() + 1, round);
  }

  public void completeRound(Integer currentRound) {
    this.rounds.get(currentRound).status = RoundStatus.COMPLETED;
  }
}
