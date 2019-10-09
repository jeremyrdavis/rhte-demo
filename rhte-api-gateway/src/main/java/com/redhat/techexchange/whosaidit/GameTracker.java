package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameTracker {

  Game game;

  private LinkedHashSet<BaseEvent> events;

  public List<Round> getRounds() {

    return Arrays.asList(
      game.getFirstRound(),
      game.getSecondRound(),
      game.getThirdRound(),
      game.getFourthRound());
  }

  public Game getGame() {

    return this.game;
  }

  @PostConstruct
  void setUp() {

    Round round1 = new Round();
    round1.setFirstQuote(new Quote("Quote #1", Quote.Author.Hamilton));
    round1.setSecondQuote(new Quote("Quote #2", Quote.Author.Shakespeare));
    round1.setThirdQuote(new Quote("Quote #3", Quote.Author.Swarzeneggar));
    round1.setFourthQuote(new Quote("Quote #4", Quote.Author.Shakespeare));
    round1.setWinner("@winningplayer");

    this.game = new Game();
    this.game.setStatus(GameStatus.STARTED);
    this.game.setFirstRound(round1);
    this.game.setSecondRound(new Round());
    this.game.setThirdRound(new Round());
    this.game.setFourthRound(new Round());

    this.events = new LinkedHashSet<>();
    this.events.add(new GameStartedEvent());
    this.events.add(new RoundStartedEvent());
    this.events.add(new NewQuoteEvent(new Quote("Quote #1", Quote.Author.Hamilton)));
    this.events.add(new GuessReceivedEvent());
    this.events.add(new NewQuoteEvent(new Quote("Quote #2", Quote.Author.Swarzeneggar)));
    this.events.add(new NewQuoteEvent(new Quote("Quote #3", Quote.Author.Shakespeare)));
    this.events.add(new RoundEndedEvent());
    this.events.add(new GameEndedEvent());

  }


  public Event getLatestEvent() {
    return (Event) this.events.toArray()[this.events.size() - 1];
  }

  public List<Event> getAllEvents() {

    return this.events.stream().collect(Collectors.toList());
  }

  public void addEvent(BaseEvent event) {

    this.events.add(event);
  }
}
