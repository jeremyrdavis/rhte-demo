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

  public void startRound() {

    Round round = new Round();
    round.status = RoundStatus.ACTIVE;

    List<Quote> allAquotes = Quote.listAll();
    Collections.shuffle(allAquotes);

    for(int i = 0; i < 4; i++){
      round.addQuote(allAquotes.get(i));
    }

    this.rounds.put(this.rounds.size() + 1, round);
  }

  public Round getCurrentRound() throws NoActiveRoundException {

    for (Round r : this.rounds.values()) {
      if (r.status == RoundStatus.ACTIVE) {
        return r;
      }
    }
    throw new NoActiveRoundException();
  }

  public Quote nextQuote() {

    return Quote.randomQuote(existingQuotes());
  }

  private Set<Quote> existingQuotes() {

    Set<Quote> existingQuotes =new HashSet<>();
    for(Round r : this.rounds.values()) {

      for(Quote q : r.quotes.values())
      existingQuotes.add(q);
    }
    return existingQuotes;
  }
}
