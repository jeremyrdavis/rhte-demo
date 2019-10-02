package com.redhat.techexchange.whosaidit.game;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class Referee {

  Long currentGameId;

  QuestionTimer timer;

  @Transactional
  public Game createGame() {

    Game game = new Game();
    game.persist();
    currentGameId = game.id;
    return game;
  }

  @Transactional
  public Game startRound() {

    Game game = Game.findById(currentGameId);
    game.startRound();
    game.persist();
    timer = new QuestionTimer();
    for (int i = 0; i < 4; i++) {
      System.out.println(game.nextQuote());
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return game;
  }

}
