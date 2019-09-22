package com.redhat.rhte.demos;

import com.redhat.rhte.demos.domain.*;
import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class GameTests {

  @Inject
  Flyway flyway;

  @Inject
  Referee gamer;

  @Test
  public void testStartGame() {

    Game game = gamer.startNewGame();
    assertEquals(Game.GameStatus.ACTIVE, game.getStatus());
  }

  @Test
  public void testGameStops() {

    Game game = new Game();
    game.start();
    assertEquals(Game.GameStatus.ACTIVE, game.getStatus());
    game.end();
    assertEquals(Game.GameStatus.ENDED, game.getStatus());
  }

  @Test
  public void testStartRound() {

    Game game = new Game();
    game.start();
    game.startRound();
    assertEquals(1, game.getRounds().size());
  }

  @Test
  public void testStopRound() {

    Game game = new Game();
    game.start();
    game.startRound();
    assertEquals(1, game.getRounds().size());
    game.getRounds().forEach(round -> {
      assertEquals(RoundStatus.ACTIVE, round.status);
    });
    game.stopRound();
    assertEquals(1, game.getRounds().size());
    game.getRounds().forEach(round -> {
      assertEquals(RoundStatus.COMPLETED, round.status);
    });
  }

  @Test
  public void testNextQuote() {

    flyway.clean();
    flyway.migrate();

    Game game = new Game();
    game.start();
    game.startRound();

    Quote quote = game.nextQuote();
    assertNotNull(quote);
    System.out.println(quote.text);
  }

  @Test
  public void testNextQuotes() {

    flyway.clean();
    flyway.migrate();

    Game game = new Game();
    game.start();
    game.startRound();

    for (int i = 0; i < 10; i++) {
      Quote quote = game.nextQuote();
      assertNotNull(quote);
      System.out.println(quote.text);
    }
  }

/*
  @Test
  public void testAddGuess() {

    flyway.clean();
    flyway.migrate();

    Game game = new Game();
    game.start();
    game.startRound();

    Quote quote = game.nextQuote();
    Guess correctGuess = new Guess("@contestant", quote.author, game.activeRound, quote);
    game.addGuess(correctGuess);

    assertNotNull(game.activeRound.guesses);
    assertEquals(1, game.activeRound.guesses.size());
    for (Guess guess: game.activeRound.guesses) {

      assertTrue(guess.isCorrect());
    }

    Guess incorrectGuess = new Guess("@anothercontestant", Authors.HAMILTON.toString(), game.activeRound, quote);
    game.addGuess(incorrectGuess);
    assertEquals(2, game.activeRound.guesses.size());

    for (Guess guess: game.activeRound.guesses) {

      if (guess.author.equals(quote.author.toString())) {

        assertTrue(guess.isCorrect());
      }else{

        assertFalse(guess.isCorrect());
      }
    }
  }
*/
}
