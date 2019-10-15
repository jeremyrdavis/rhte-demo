package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.Event;
import com.redhat.techexchange.whosaidit.game.domain.Round;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RoundStartedEventHandler {

  @Inject
  @RestClient
  ApiGatewayService apiGatewayService;

  @Inject
  @RestClient
  HistoryService historyService;

  public void handle(Round round) {

    Event roundStartedEvent = new Event();
    apiGatewayService.sendEvent(roundStartedEvent);
    historyService.sendEvent(roundStartedEvent);
  }

}