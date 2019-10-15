package com.redhat.techexchange.whosaidit.domain;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.config.PropertyOrderStrategy;
import java.util.HashMap;

@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class Game {

  GameStatus status;

  HashMap<Integer, Round> rounds;

  public GameStatus getStatus() {
    return status;
  }

  public void setStatus(GameStatus status) {
    this.status = status;
  }

  public HashMap<Integer, Round> getRounds() {
    return rounds;
  }

  public void setRounds(HashMap<Integer, Round> rounds) {
    this.rounds = rounds;
  }
}
