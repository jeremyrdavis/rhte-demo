package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.Event;
import com.redhat.techexchange.whosaidit.game.domain.Game;
import com.redhat.techexchange.whosaidit.game.domain.StatusUpdate;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Date;

@ApplicationScoped
public class GameStartedEventHandler {

  @Inject
  @RestClient
  ApiGatewayService apiGatewayService;

  @Inject
  @RestClient
  TwitterService twitterService;

  @Inject
  @RestClient
  HistoryService historyService;

  void handle(Game game) {

    Event gameStartedEvent = new Event();
    gameStartedEvent.timestamp = Date.from(Instant.now());

    // Update ApiGateway
    Response apiGatewayResponse = apiGatewayService.sendEvent(gameStartedEvent);
    if (apiGatewayResponse.getStatus() != 200)
      throw new RuntimeException(String.valueOf(apiGatewayResponse.getStatus()));

    // Update TwitterService
    Response twitterServiceResponse = twitterService.sendStatusUpdate(new StatusUpdate("Game Started!"));
    if (twitterServiceResponse.getStatus() != 200)
      throw new RuntimeException(String.valueOf(twitterServiceResponse.getStatus()));

    // Log to HistoryService
    Response historyServiceResponse = historyService.sendEvent(gameStartedEvent);
    if (historyServiceResponse.getStatus() != 200)
      throw new RuntimeException(String.valueOf(historyServiceResponse.getStatus()));

  }

}
