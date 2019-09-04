package com.redhat.rhte.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.*;

@Entity
public class Game extends PanacheEntity {

  @Enumerated
  GameStatus status;

  @Transient
  Round activeRound;

  @OneToMany
  @JoinColumn(name = "game_id")
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
  }

  public Quote nextQuote() {

    Quote nextQuote;

    if (this.activeRound.quotes.size() >= 1) {

      List<Quote> existingQuotes = new ArrayList<Quote>();
      existingQuotes.addAll(this.activeRound.quotes);
      nextQuote = Quote.randomNewQuote(existingQuotes);
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


  public void addGuess(Guess guess) {

    verifyStatus();
    this.activeRound.addGuess(guess);
  }

  private void verifyStatus() {

    if (this.status != GameStatus.ACTIVE) {

      throw new RuntimeException("Game must be started before adding guesses");
    } else if (this.activeRound.status != Round.RoundStatus.ACTIVE) {

      throw new RuntimeException("Rounds must be active before adding guesses");
    }
  }

  public enum GameStatus {

    ACTIVE, ENDED;
  }

}
