package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.Round;
import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Mock
@ApplicationScoped
public class MockRoundStartedEventHandler extends RoundStartedEventHandler {

  @Override
  public void handle(Round round) {
    System.out.println("Mock handle");
    assertNotNull(round);
  }
}
