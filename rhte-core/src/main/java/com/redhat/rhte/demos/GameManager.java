package com.redhat.rhte.demos;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class GameManager {

  Game game;

  @Transactional
  public Game startGame() {

    if (game == null) {
      game = new Game();
    }
    game.start();
    return game;
  }

  @Transactional
  public Game getCurrentGame() {

    if (game == null) {
      game = new Game();
      game.persist();
    }
    return this.game;
  }
}
