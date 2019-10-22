package com.redhat.techexchange.whosaidit.game.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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

    Game game = new Game(mockQuotes());
    assertEquals(GameStatus.CREATED, game.status);
    game.start();
    assertEquals(GameStatus.IN_PROGRESS, game.status);
  }

  List<Quote> mockQuotes() {
    List<Quote> retVal = new ArrayList<>(16);
    for (int i = 0; i < 20; i++) {
      retVal.add(new Quote("Quote#" + i, Author.Hamilton));
    }
    return retVal;
  }

}
