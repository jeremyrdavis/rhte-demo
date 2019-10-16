package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.*;
import com.redhat.techexchange.whosaidit.infrastructure.EventsSocket;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.axle.core.Vertx;
import io.vertx.axle.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameTracker {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  int currentRound = 1;

  @Inject
  EventsSocket eventsSocket;

  @Inject
  EventBus eventBus;

  @Inject
  Vertx vertx;

  Game game;

  LinkedHashSet<BaseEvent> events;

  public Game getGame() {

    return this.game;
  }

  @PostConstruct
  void setUp() {

    HashMap<Integer, Round> rounds = new HashMap<>(4);

    for (int i = 1; i < 4; i++) {
      Round round = new Round();
      round.quotes = new HashMap<Integer, Quote>();
      for (int j = 0; j < 4; j++) {
        if (j % 2 == 0) {
          round.quotes.put(j, new Quote("Quote #" + j, Quote.Author.Hamilton));
        } else {
          round.quotes.put(j, new Quote("Quote #" + j, Quote.Author.Shakespeare));
        }
      }
      round.setWinner("@winningplayer#" + i);
      rounds.put(i, round);
    }


/*
    this.game = new Game();
    this.game.setStatus(GameStatus.STARTED);
    this.game.setRounds(rounds);

    this.events = new LinkedHashSet<>();
    this.events.add(new GameStartedEvent());
    this.events.add(new RoundStartedEvent());
    this.events.add(new NextQuoteEvent(EventType.NextQuoteEvent, new Quote("Quote #1", Quote.Author.Hamilton)));
    this.events.add(new GuessReceivedEvent());
    this.events.add(new NextQuoteEvent(EventType.NextQuoteEvent, new Quote("Quote #2", Quote.Author.Swarzeneggar)));
    this.events.add(new NextQuoteEvent(EventType.NextQuoteEvent, new Quote("Quote #3", Quote.Author.Shakespeare)));
    this.events.add(new RoundEndedEvent());
    this.events.add(new GameEndedEvent());
*/

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

  @ConsumeEvent("roundStart")
  public void startRound(Game game) {

    logger.debug("startRound: " + currentRound);
    Round round = this.game.getRounds().get(currentRound);
//    eventsSocket.broadcast(new RoundStartedEvent());
/*
    for (int i = 0; i < 4; i++) {
      Quote q = round.quotes.get(i);
      System.out.println("next quote: " + q.text + " " + q.getAuthor().name());
      vertx.setTimer(5000, l -> {
        eventsSocket.broadcast(new NextQuoteEvent(EventType.NextQuoteEvent, q));
      });
    }
    round.setWinner("@winner");
    eventsSocket.broadcast(new RoundEndedEvent());
*/
    currentRound++;
  }

  @ConsumeEvent("roundEnd")
  public void endRound(Game game) {

    logger.debug("endRound: " + currentRound);
    Round round = this.game.getRounds().get(currentRound);
//    round.setWinner("@winner");
//    eventsSocket.broadcast(new RoundEndedEvent(EventType.RoundEndedEvent, round));
    currentRound++;
  }

  public void startGame() {

  }
}
