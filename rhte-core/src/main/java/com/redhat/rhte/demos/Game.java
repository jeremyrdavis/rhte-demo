package com.redhat.rhte.demos;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game {

  boolean status;

  Set<Round> rounds;

  Set<Quote> quotes;

  public Game() {

    this.quotes = new HashSet<>();
    this.quotes.addAll(Quote.findAll().list());
  }

  public void start() {

    this.status = true;
  }

  public void end() {

    this.status = false;
  }

  public void startRound() {

    if (rounds == null) {
      rounds = new HashSet<Round>();
    }
    Round round = new Round();
    round.start();
    rounds.add(round);
  }

  public void stopRound() {

    rounds.forEach(round -> {
      if (round.status.equals(Round.RoundStatus.ACTIVE)) {
        round.stop();
      }
    });
  }

  public Quote nextQuote() {

    List<Quote> quotes = Quote.findAll().list();
//    Quote quote = Quote.findById(id);
    int i = new Random().nextInt(quotes.size() - 1);
    return quotes.get(i);
  }

  public enum GameStatus {

    ACTIVE, ENDED;
  }

}
