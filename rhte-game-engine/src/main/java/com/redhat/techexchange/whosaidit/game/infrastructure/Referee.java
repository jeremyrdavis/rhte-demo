package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.*;
import com.redhat.techexchange.whosaidit.game.infrastructure.GameStartedEventHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class Referee {

  @Inject
  GameStartedEventHandler gameStartedEventHandler;

  Long currentGameId;

  Integer currentRound = 1;

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

    for (int i = 0; i < 4; i++) {
      Quote q = round.quotes.get(i + 1);
      System.out.println(q.text);
      onNextQuote(q);
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      round.status = RoundStatus.COMPLETED;
    }

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
/*
    System.out.println("Next quote: " + quote);
    Response twitterServiceResponse = twitterService.sendStatusUpdate(new StatusUpdate("Test Status"));
    Response apiGatewayResponse = apiGatewayService.sendStatusUpdate(new StatusUpdate("Test Status"));
    System.out.println("Calling ApiGateway");
    if (twitterServiceResponse.getStatus() != 200)
      throw new RuntimeException(String.valueOf(twitterServiceResponse.getStatus()));
    if (apiGatewayResponse.getStatus() != 200)
      throw new RuntimeException(String.valueOf(apiGatewayResponse.getStatus()));
*/
  }

  void onGameStart(Game game) {
    gameStartedEventHandler.handle(game);
  }

}
