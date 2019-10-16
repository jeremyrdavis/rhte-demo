package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.*;
import com.sun.media.jfxmedia.events.PlayerStateEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
public class Referee {

  @Inject
  GameStartedEventHandler gameStartedEventHandler;

  @Inject
  RoundStartedEventHandler roundStartedEventHandler;

  @Inject
  NextQuoteEventHandler nextQuoteEventHandler;

  Long currentGameId;

  Integer currentRound = 1;

  Set<Player> players = new HashSet<>();

  @Transactional

  public Game createGame() {

    List<Quote> allQuotes = Quote.listAll();
    Collections.shuffle(allQuotes);

    Game game = new Game();

    for (int i = 0; i < 4; i++) {
      Round r = new Round();
      r.status = RoundStatus.CREATED;
      for (int j = 0; j < 4; j++) {
        Quote quote = allQuotes.remove(j);
        r.addQuote(quote);
      }
      game.addRound(r);
    }

    game.persist();
    onGameStart(game);
    currentGameId = game.id;
    return game;
  }

  @Transactional
  public Game startRound() {

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
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    for (int i = 0; i < 5; i++) {
      players.add(new Player("Player#" + i));
    }

    List<Player> playersList = new ArrayList<>(players);
    Collections.shuffle(playersList);
    Player winner = playersList.get(0);
    round.winner = winner.getName();
    game.completeRound(currentRound);
    currentRound++;
    game.persist();
    return game;
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

  void onNextQuote(Quote quote) {
    nextQuoteEventHandler.handle(quote);
    System.out.println("onNextQuote");
  }

  void onGameStart(Game game) {
    gameStartedEventHandler.handle(game);
    System.out.println("onGameStart");
  }

  private void onRoundStart(Round round) {
    System.out.println("onRoundStart");
    roundStartedEventHandler.handle(round);
  }

  public void testQuote() {
    onNextQuote(new Quote("Test " + UUID.randomUUID().toString(), Author.Shakespeare));
  }


}
