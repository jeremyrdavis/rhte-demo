package com.redhat.rhte.demos;

import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class GameTests {

  @Inject
  Flyway flyway;

  @Test
  public void testStartGame() {

    Game game = new Game();
    game.start();
    assertEquals(Game.GameStatus.ACTIVE, game.status);
  }

  @Test
  public void testGameStops() {

    Game game = new Game();
    game.start();
    assertEquals(Game.GameStatus.ACTIVE, game.status);
    game.end();
    assertEquals(Game.GameStatus.ENDED, game.status);
  }

  @Test
  public void testStartRound() {

    Game game = new Game();
    game.start();
    game.startRound();
    assertEquals(1, game.rounds.size());
  }

  @Test
  public void testStopRound() {

    Game game = new Game();
    game.start();
    game.startRound();
    assertEquals(1, game.rounds.size());
    game.rounds.forEach(round -> {
      assertEquals(1, round.id);
    });
    game.stopRound();
    assertEquals(1, game.rounds.size());
  }

  @Test
  public void testNextQuote() {

    flyway.clean();
    flyway.migrate();

    Game game = new Game();

    for (int i = 0; i < 10; i++) {
      Quote quote = game.nextQuote();
      assertNotNull(quote);
      System.out.println(quote.text);
    }
  }
}
