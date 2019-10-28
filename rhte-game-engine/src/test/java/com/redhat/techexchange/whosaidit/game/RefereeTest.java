package com.redhat.techexchange.whosaidit.game;

import com.redhat.techexchange.whosaidit.game.domain.GameCreatedEvent;
import com.redhat.techexchange.whosaidit.game.domain.GameStartedEvent;
import com.redhat.techexchange.whosaidit.game.domain.Round;
import com.redhat.techexchange.whosaidit.game.infrastructure.Referee;
import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    GameCreatedEvent gameCreatedEvent = referee.createGame();
    assertNotNull(gameCreatedEvent);
    assertNotNull(gameCreatedEvent.getGame().id);
    assertNotNull(gameCreatedEvent.getGame().rounds);
    assertEquals(4, gameCreatedEvent.getGame().rounds.size());
    for (Round round : gameCreatedEvent.getGame().rounds.values()) {
      assertEquals(4, round.quotes.size());
      System.out.println(round);
    }
  }

  @Test
  public void testStartGame() {
    GameCreatedEvent gameCreatedEvent = referee.createGame();
    GameStartedEvent gameStartedEvent = referee.startGame(gameCreatedEvent.getGame().id);
  }

  @Test
  public void testCompleteRound() {

  }

}
