package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.*;
import com.redhat.techexchange.whosaidit.infrastructure.EventsSocket;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.axle.core.Vertx;
import io.vertx.axle.core.eventbus.EventBus;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameTracker {

  int currentRound = 1;

  @Inject
  EventsSocket eventsSocket;

  @Inject
  EventBus eventBus;

  @Inject
  Vertx vertx;

  Game game;

  private LinkedHashSet<BaseEvent> events;

  public Game getGame() {

    return this.game;
  }

  @PostConstruct
  void setUp() {

    HashMap<Integer, Round> rounds = new HashMap<>(4);

    for (int i = 1; i < 4; i++) {
      Round round = new Round();
      round.quotes = new HashMap<Integer, Quote>();
      if (i % 2 == 0) {
        round.quotes.put(i, new Quote("Quote #" + i, Quote.Author.Hamilton));
      } else {
        round.quotes.put(i, new Quote("Quote #" + i, Quote.Author.Shakespeare));
      }
      round.setWinner("@winningplayer#" + i);
      rounds.put(i, round);
    }


    this.game = new Game();
    this.game.setStatus(GameStatus.STARTED);
    this.game.setRounds(rounds);

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

  public void startGame() {
    System.out.println("GameTracker.startGame");
  }

  @ConsumeEvent("roundStart")
  public void startRound(Game game) {

    Round round = this.game.getRounds().get(currentRound);

    for (int i = 0; i < 4; i++) {

        Quote q = round.quotes.get(i + 1);
        System.out.println(q.text);
        vertx.setTimer(5, l -> {
          eventsSocket.broadcast(new NewQuoteEvent(q));
        });
      }
      currentRound++;
    }
}
