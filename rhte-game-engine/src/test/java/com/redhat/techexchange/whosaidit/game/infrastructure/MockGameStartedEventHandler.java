package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.Game;
import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Mock
@ApplicationScoped
public class MockGameStartedEventHandler extends GameStartedEventHandler{

  @Override
  void handle(Game game) {
    System.out.println("Mock handle");
    assertNotNull(game);
  }

}
