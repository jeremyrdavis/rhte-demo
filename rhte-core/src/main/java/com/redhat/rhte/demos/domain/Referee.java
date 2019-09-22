package com.redhat.rhte.demos.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class Referee {

  private Game currentGame;

  @Transactional
  public Game findById(long id) {

    if (this.currentGame.id.equals(id)) {

      return this.currentGame;
    }else{

      return Game.findById(id);
    }
  }

  @Transactional
  public Game startNewGame() {

    Game newGame = new Game();
    newGame.status = Game.GameStatus.ACTIVE;
    newGame.persist();
    this.currentGame = newGame;
    return this.currentGame;
  }

  @Transactional
  public Game startGame(long id) throws GameNotFoundException {

    if (this.currentGame.id.equals(id)) {

      this.currentGame.start();
      this.currentGame.persist();
      return this.currentGame;
    }else{

      Game game = Game.findById(id);
      if (game == null) {

        throw new GameNotFoundException();
      }
      game.start();
      return game;
    }
  }
}
