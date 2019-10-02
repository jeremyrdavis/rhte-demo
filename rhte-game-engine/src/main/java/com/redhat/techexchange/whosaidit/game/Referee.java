package com.redhat.techexchange.whosaidit.game;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class Referee {

  Long currentGameId;

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
    return game;
  }


}
