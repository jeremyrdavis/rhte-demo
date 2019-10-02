package com.redhat.techexchange.whosaidit.game;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class Referee {

  Long currentGameId;

  @Inject
  QuestionTimer timer;

  @Transactional
  public Game createGame() {

    Game game = new Game();
    game.persist();
    currentGameId = game.id;
    return game;
  }

  public Game startRound() {

    Game game = initializeRound();
    try {
      timer.setQuotes(game.getCurrentRound().quotes);
      timer.activate();
    } catch (NoActiveRoundException e) {
      e.printStackTrace();
    }
/*
    for (int i = 0; i < 4; i++) {
      System.out.println(game.nextQuote());
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
*/
    return game;
  }

  @Transactional
  private Game initializeRound(){
    Game game = Game.findById(currentGameId);
    game.startRound();
    game.persist();
    return game;
  }

}
