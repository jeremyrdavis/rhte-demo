package com.redhat.techexchange.whosaidit.game;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class Referee {

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

  void onNextQuote(Quote quote) {
    System.out.println("Next quote: " + quote);
    System.out.println("Calling Twiiter");
    System.out.println("Calling ApiGateway");
  }

  @Transactional
  private Game initializeRound(){
    Game game = Game.findById(currentGameId);
    game.startRound(currentRound);
    game.persist();
    return game;
  }

}
