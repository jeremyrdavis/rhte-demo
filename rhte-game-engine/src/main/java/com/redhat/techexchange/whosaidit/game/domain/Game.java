package com.redhat.techexchange.whosaidit.game.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "game")
public class Game extends PanacheEntity {

  public GameStatus status;

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

  public static GameCreatedEvent createGame(Map<Integer, Round> roundsToSet) {
    Game game = new Game();
    game.status = GameStatus.CREATED;
    game.rounds = roundsToSet;
    return new GameCreatedEvent(game);
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
    this.status = GameStatus.IN_PROGRESS;
    this.rounds.get(1).status = RoundStatus.ACTIVE;
  }
}
