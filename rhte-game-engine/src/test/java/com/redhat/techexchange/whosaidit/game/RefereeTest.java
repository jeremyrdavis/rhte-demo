package com.redhat.techexchange.whosaidit.game;

import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class RefereeTest {

  @Inject
  Referee referee;

  @Inject
  Flyway flyway;

  @BeforeEach
  public void setUp() {
    flyway.clean();
    flyway.migrate();
  }

  @Test
  public void testCreateGame() {

    Game game = referee.createGame();
    assertNotNull(game);
    assertNotNull(game.id);
    assertNotNull(game.rounds);
    assertEquals(0, game.rounds.size());
  }

  @Test
  public void testStartRound() {

    Game game = referee.createGame();
    game = referee.startRound();
    assertEquals(1, game.rounds.size());
    assertEquals(4, game.rounds.get(1).quotes.size());
  }

  @Test
  public void testQuestionGeneration() {

    Game game = referee.createGame();
    game = referee.startRound();
    try {
      Round currentRound = game.getCurrentRound();
      assertEquals(1, game.rounds.size());
      Quote quote = game.nextQuote();
      assertNotNull(quote);
      Quote quote2 = game.nextQuote();
      assertNotNull(quote2);
      assertNotEquals(quote, quote2);
    } catch (NoActiveRoundException e) {
      assertNull(e);
    }
  }

  @Test
  public void testAutomaticQuestionGeneration() {

    Game game = referee.createGame();
    game = referee.startRound();

    await().atLeast(35, SECONDS);
  }
}
