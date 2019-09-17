package com.redhat.rhte.demos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Game extends PanacheEntity {

  @JsonProperty("name")
  String name;

  @JsonProperty("status")
  @Enumerated(EnumType.STRING)
  GameStatus status;

  @JsonProperty("activeRound")
  @Transient
  Round activeRound;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  @Cascade(CascadeType.ALL)
  Set<Round> rounds;

  public Game() {

    if (this.rounds == null) {

      this.rounds = new HashSet<>();
    }else{
      for (Round r : this.rounds) {
        if (r.status.equals(RoundStatus.ACTIVE)) {
          this.activeRound = r;
        }
      }
    }
  }

  public static Game create(Game game) {

    if (nameIsAvailable(game.name)) {

      game.persistAndFlush();
    }else{

      throw new RuntimeException("A game with name \'" + game.name + "\' already exists");
    }
    return game;
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

  @JsonIgnore
  public void stopRound() {

    if (this.activeRound == null) {
      for (Round r : this.rounds) {
        if (r.status.equals(RoundStatus.ACTIVE)) {
          this.activeRound = r;
        }
      }
    }
    this.activeRound.stop();
    this.activeRound = null;
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

    RoundStatus activeRoundStatus = this.activeRound.status;
    RoundStatus active = RoundStatus.ACTIVE;

    if (!(this.status.equals(GameStatus.ACTIVE))) {

      throw new RuntimeException("Game must be started before adding guesses");
    } else if (!this.activeRound.status.equals(RoundStatus.ACTIVE)) {

      throw new RuntimeException("Rounds must be active before adding guesses");
    }
  }

  public static boolean nameIsAvailable(String name) {

    if (Game.count("name", name) >= 1) {
      return false;
    }
    return true;
  }

  public enum GameStatus {

    CREATED("created"), ACTIVE("active"), ENDED("ended");

    @JsonValue
    String name;

    GameStatus(String name) {
      this.name = name;
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GameStatus getStatus() {
    return status;
  }

  public void setStatus(GameStatus status) {
    this.status = status;
  }

  public Round getActiveRound() {
    return activeRound;
  }

  public void setActiveRound(Round activeRound) {
    this.activeRound = activeRound;
  }

  public Set<Round> getRounds() {
    return rounds;
  }

  public void setRounds(Set<Round> rounds) {
    this.rounds = rounds;
  }
}
