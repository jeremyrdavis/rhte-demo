package com.redhat.techexchange.whosaidit.game;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class Referee {

  @Transactional
  public Game createGame() {

    Game game = new Game();
    game.rounds.add(new Round());
    game.persist();
    return game;
  }
}
