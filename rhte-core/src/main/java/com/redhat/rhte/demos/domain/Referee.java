package com.redhat.rhte.demos.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class Referee {

  private Game currentGame;

  @Transactional
  public Game startRound(long id) throws GameNotFoundException {

    Game game = findGame(id);
    game.startRound();
    game.persist();
    return game;
  }

  private Game findGame(long id) throws GameNotFoundException {

    if (this.currentGame.id.equals(id)) {

      return this.currentGame;
    } else {

    }
    Game game = Game.findById(id);
    if (game == null) {

      throw new GameNotFoundException();
    }
    return game;
  }

  @Transactional
  public Game findById(long id) throws GameNotFoundException {

    return findGame(id);
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

    Game game = findGame(id);
    game.start();
    return game;
  }

  public Game endGame(long id) throws GameNotFoundException {

    Game game = findGame(id);
    game.end();
    return game;
  }
}
