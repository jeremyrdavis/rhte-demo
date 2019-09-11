package com.redhat.rhte.demos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.*;

@Entity
public class Game extends PanacheEntity {

  @JsonProperty("name")
  public String name;

  @JsonProperty("status")
  @Enumerated(EnumType.STRING)
  public GameStatus status;

  @JsonProperty("activeRound")
  @Transient
  public Round activeRound;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  @Cascade(CascadeType.ALL)
  @JsonBackReference
  public Set<Round> rounds;

  public Game() {

    if (this.rounds == null) {

      this.rounds = new HashSet<>();
    }else{
      for (Round r : this.rounds) {
        if (r.status.equals(Round.RoundStatus.ACTIVE)) {
          this.activeRound = r;
        }
      }
    }
  }

  public void start() {

    this.status = GameStatus.ACTIVE;
  }

  public void end() {

    this.status = GameStatus.ENDED;
  }

  public void startRound() {

    if (!(this.status.equals(GameStatus.ACTIVE))) {
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

    if (this.activeRound == null) {
      for (Round r : this.rounds) {
        if (r.status.equals(Round.RoundStatus.ACTIVE)) {
          this.activeRound = r;
        }
      }
    }
    this.activeRound.stop();
  }

  @JsonIgnore
  public Quote nextQuote() {

    Quote nextQuote;

    if (this.activeRound.quotes.size() >= 1) {

      List<Quote> existingQuotes = new ArrayList<Quote>();
      existingQuotes.addAll(this.activeRound.quotes);
      nextQuote = Quote.randomNewQuote(existingQuotes);
    } else {

      nextQuote = Quote.randomNewQuote();
    }

    this.activeRound.addQuote(nextQuote);
    return nextQuote;
  }

  @JsonIgnore
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

    Round.RoundStatus activeRoundStatus = this.activeRound.status;
    Round.RoundStatus active = Round.RoundStatus.ACTIVE;

    if (!(this.status.equals(GameStatus.ACTIVE))) {

      throw new RuntimeException("Game must be started before adding guesses");
    } else if (!this.activeRound.status.equals(Round.RoundStatus.ACTIVE)) {

      throw new RuntimeException("Rounds must be active before adding guesses");
    }
  }

  public enum GameStatus {

    CREATED("created"), ACTIVE("active"), ENDED("ended");

    @JsonValue
    String name;

    GameStatus(String name) {
      this.name = name;
    }
  }

}
