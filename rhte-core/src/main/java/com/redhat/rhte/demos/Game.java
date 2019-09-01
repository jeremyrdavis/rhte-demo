package com.redhat.rhte.demos;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class Game {

  GameStatus status;

  Round activeRound;

  Set<Round> rounds;

  public Game() {

    this.rounds = new HashSet<>();
  }

  public void start() {

    this.status = GameStatus.ACTIVE;
  }

  public void end() {

    this.status = GameStatus.ENDED;
  }

  public void startRound() {

    if (this.status != GameStatus.ACTIVE) {
      throw new RuntimeException("The Game is not started");
    }

    if (rounds == null) {
      rounds = new HashSet<Round>();
    }
    Round round = new Round();
    round.start();
    this.activeRound = round;
    rounds.add(round);
  }

  public void stopRound() {

    this.activeRound.stop();
/*

    rounds.forEach(round -> {
      if (round.status.equals(Round.RoundStatus.ACTIVE)) {
        round.stop();
      }
    });
*/
  }

  public Quote nextQuote() {

    Quote nextQuote;
    if (this.activeRound.quotes.size() >= 1) {

      List<Long> quoteIds = this.activeRound.quotes.stream()
        .map(q -> { return q.id; })
        .collect(toList());
      nextQuote = Quote.randomNewQuote(quoteIds);
    }else {

      nextQuote = Quote.randomNewQuote();
    }

    this.activeRound.addQuote(nextQuote);
    return nextQuote;
  }

  private Quote randomQuote() {

    List<Quote> quotes = Quote.findAll().list();
    int i = new Random().nextInt(quotes.size() - 1);
    return quotes.get(i);
  }

  public enum GameStatus {

    ACTIVE, ENDED;
  }

}
