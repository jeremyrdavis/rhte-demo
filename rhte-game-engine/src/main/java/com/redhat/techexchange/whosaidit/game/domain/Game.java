package com.redhat.techexchange.whosaidit.game.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Game extends PanacheEntity {

  public GameStatus gameStatus;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  @Cascade(CascadeType.ALL)
  public Map<Integer, Round> rounds = new HashMap<>();

  public void startRound(Integer currentRound) {
    this.rounds.get(currentRound).status = RoundStatus.ACTIVE;
  }

  public Map<Integer, Round> getRounds() {
    return this.rounds;
  }

  public Game() {
    this.gameStatus = GameStatus.CREATED;
  }

  public Game(List<Quote> quotes) {
    this.gameStatus = GameStatus.CREATED;
    for (int i = 0; i < 4; i++) {
      Round r = new Round();
      r.status = RoundStatus.CREATED;
      for (int j = 0; j < 4; j++) {
        Quote quote = quotes.remove(j);
        r.addQuote(quote);
      }
      this.addRound(r);
    }
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

  public void start() {
    this.gameStatus = GameStatus.IN_PROGRESS;
    this.rounds.get(1).status = RoundStatus.ACTIVE;
  }
}
