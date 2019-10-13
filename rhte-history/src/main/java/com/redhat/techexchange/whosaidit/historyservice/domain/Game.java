package com.redhat.techexchange.whosaidit.historyservice.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Game extends PanacheEntity {

    HashMap<Integer, Round> rounds = new HashMap<>();

    public void startRound(Integer currentRound) {
      this.rounds.get(currentRound).status = RoundStatus.ACTIVE;
    }

    public Map<Integer, Round> getRounds() {
      return this.rounds;
    }

}
