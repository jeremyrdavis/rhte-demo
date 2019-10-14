package com.redhat.techexchange.whosaidit;

import com.redhat.techexchange.whosaidit.domain.*;
import com.redhat.techexchange.whosaidit.infrastructure.EventsSocket;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameTracker {

  @Inject
  EventsSocket eventsSocket;

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
    round1.quotes = new HashMap<Integer, Quote>();
    for(int i=1;i<4;i++){
      if(i % 2 == 0){
        round1.quotes.put(i, new Quote("Quote #" + i, Quote.Author.Hamilton));
      }else{
        round1.quotes.put(i, new Quote("Quote #" + i, Quote.Author.Shakespeare));
      }
    }
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

  public void start() {

    List<Round> rounds = getRounds();
    for (int i = 0; i < 4; i++) {
      Round round = rounds.get(i);
      Quote q = round.quotes.get(i + 1);
      System.out.println(q.text);
      eventsSocket.broadcast(new NewQuoteEvent(q));
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }
}
