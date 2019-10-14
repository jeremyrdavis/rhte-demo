package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.Quote;
import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Mock
@ApplicationScoped
public class MockNextQuoteEventHandler extends NextQuoteEventHandler{


  @Override
  public void handle(Quote quote) {

    assertNotNull(quote);
  }
}
