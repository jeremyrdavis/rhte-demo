package com.redhat.techexchange.whosaidit.game.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class GameEventsTest {

  @Inject
  Flyway flyway;

  @BeforeEach
  public void setUp() {
    flyway.clean();
    flyway.migrate();
  }


  @Test
  public void testGameStartEvent() {

    Game game = new Game(mockRounds());
    assertEquals(GameStatus.CREATED, game.status);
    game.start();
    assertEquals(GameStatus.IN_PROGRESS, game.status);
  }

  Map<Integer, Round> mockRounds() {
    Map<Integer, Round> retVal = new HashMap<>();
    for (int i = 0; i < 4; i++) {
      retVal.put(i, new Round());
    }
    return retVal;
  }

}
