package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.*;
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
public class RoundStartedEventHandler {

  @Inject
  @RestClient
  TwitterService twitterService;

  @Inject
  @RestClient
  ApiGatewayService apiGatewayService;

  @Inject
  @RestClient
  HistoryService historyService;

  public void handle(Round round) {

    RoundStartedEvent roundStartedEvent = new RoundStartedEvent(EventType.RoundStartedEvent, round);

    //call Api Gateway
    CompletionStage<Response> apiGatewayResponse = apiGatewayService.sendRoundStartedEvent(roundStartedEvent)
      .thenApply(r -> {
        if (r.getStatus() != 200) {
          throw new RuntimeException(String.valueOf(r.getStatus()));
        }
        return r;
      });

    //log to History
    CompletionStage<Response> historyServiceResponse = historyService.sendEvent(roundStartedEvent)
      .thenApply(r -> {
        if (r.getStatus() != 200) {
          throw new RuntimeException(String.valueOf(r.getStatus()));
        }
        return r;
      });

    //send update to Twitter
    // Update TwitterService
    StringBuilder builder = new StringBuilder()
      .append("WhoSaidIt? Next Round!")
      .append("\n")
      .append(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"))));

    CompletionStage<Response> twitterServiceResponse = twitterService.sendStatusUpdate(new StatusUpdate(builder.toString()))
      .thenApply(r -> {
        if (r.getStatus() != 201) {
          throw new RuntimeException(String.valueOf(r.getStatus()));
        }
        return r;
      });

  }

}
