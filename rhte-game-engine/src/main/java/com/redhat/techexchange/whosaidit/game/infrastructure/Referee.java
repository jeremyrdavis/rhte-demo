package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
public class Referee {

  @Inject
  GameStartedEventHandler gameStartedEventHandler;

  @Inject
  RoundStartedHandler roundStartedHandler;

  @Inject
  RoundEndedEventHandler roundEndedEventHandler;

  @Inject
  NextQuestionHandler nextQuoteEventHandler;

  Long currentGameId;

  Integer currentRound = 1;

  Set<String> players = new HashSet<>();

  @Transactional
  public Game createGame() {

    List<Quote> allQuotes = Quote.listAll();
    Collections.shuffle(allQuotes);

    Game game = new Game(allQuotes);
    game.persist();
    onGameStart(game);
    currentGameId = game.id;
    return game;
  }

  public void startRound() {

    if (currentGameId == null) {
      createGame();
    }

    Game game = Game.findById(currentGameId);
    Round round = game.rounds.get(currentRound);

    onRoundStart(round);

    for (int i = 0; i < 4; i++) {
      Quote q = round.quotes.get(i + 1);
      System.out.println(q.text);
      onNextQuote(q);
      try {
        Thread.sleep(30000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    game.completeRound(currentRound);
    currentRound++;
    addWinner();
  }

  @Transactional
  void addWinner() {
    Game game = Game.findById(currentGameId);
    Round round = game.getRounds().get(4);
    round.winner = pickWinner();
    game.persist();
    onRoundEnd(round);
  }

  private String pickWinner() {

    List<String> playersList = new ArrayList<>(players);
    // add a couple of people to make sure we have someone when testing
    if (playersList.isEmpty()) {
      playersList.add("@Tortugasl");
      playersList.add("@thedesignatic");
    }
    Collections.shuffle(playersList);
    return playersList.get(0);
  }

  public Round getCurrentRound() {
    Game game = Game.findById(currentGameId);
    return game.getCurrentRound();
  }

  @Transactional
  private Game initializeRound() {
    Game game = Game.findById(currentGameId);
    game.startRound(currentRound);
    game.persist();
    return game;
  }

  public void addPlayer(String player) {
    this.players.add(player);
  }

  void onNextQuote(Quote quote) {
    NextQuoteEvent event = new NextQuoteEvent(EventType.NextQuoteEvent, quote);
    nextQuoteEventHandler.handle(event);
    System.out.println("onNextQuote");
  }

  void onGameStart(Game game) {
    gameStartedEventHandler.handle(game);
    System.out.println("onGameStart");
  }

  private void onRoundStart(Round round) {
    System.out.println("onRoundStart");
    roundStartedHandler.handle(new RoundStartedEvent(EventType.RoundStartedEvent, round));
  }

  void onRoundEnd(Round round) {
    System.out.println("onRoundEnd");
    roundEndedEventHandler.handle(round);
  }

  public void testQuote() {
    onNextQuote(new Quote("Test " + UUID.randomUUID().toString(), Author.Shakespeare));
  }


}
