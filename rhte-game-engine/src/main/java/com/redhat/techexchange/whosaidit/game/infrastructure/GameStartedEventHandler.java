package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.DomainEventType;
import com.redhat.techexchange.whosaidit.game.domain.Game;
import com.redhat.techexchange.whosaidit.game.domain.GameStartedEvent;
import com.redhat.techexchange.whosaidit.game.domain.StatusUpdate;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.CompletionStage;

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

    GameStartedEvent gameStartedEvent = new GameStartedEvent();
    gameStartedEvent.setDomainEventType(DomainEventType.GameStartedEvent);
    gameStartedEvent.setGame(game);
//    gameStartedEvent.timestamp = Date.from(Instant.now());

/*
    // Update ApiGateway
    Response apiGatewayResponse = apiGatewayService.sendEvent(gameStartedEvent);
    if (apiGatewayResponse.getStatus() != 200)
      throw new RuntimeException(String.valueOf(apiGatewayResponse.getStatus()));
*/

    CompletionStage<Response> apiGatewayResponse = apiGatewayService.sendGameStartedEvent()
      .thenApply(r -> {
        if (r.getStatus() != 200) {
          throw new RuntimeException(String.valueOf(r.getStatus()));
        }
        return r;
      });


    // Update TwitterService
    StringBuilder builder = new StringBuilder()
      .append("WhoSaidIt? Game Started!")
      .append("\n")
      .append(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).format(ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"))));

    StatusUpdate statusUpdate = new StatusUpdate(builder.toString());
    CompletionStage<Response> twitterServiceResponse = twitterService.sendStatusUpdate(statusUpdate)
      .thenApply(r -> {
        if (r.getStatus() != 201) {
          throw new RuntimeException(String.valueOf(r.getStatus()));
        }
        return r;
      });

    // Log to HistoryService
    CompletionStage<Response> historyServiceResponse = historyService.sendEvent(gameStartedEvent)
      .thenApply(r -> {
        if (r.getStatus() != 200) {
          throw new RuntimeException(String.valueOf(r.getStatus()));
        }
        return r;
      });

  }

}
