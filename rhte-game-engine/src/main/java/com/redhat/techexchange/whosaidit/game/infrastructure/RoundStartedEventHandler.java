package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.DomainEventType;
import com.redhat.techexchange.whosaidit.game.domain.Round;
import com.redhat.techexchange.whosaidit.game.domain.RoundStartedEvent;
import com.redhat.techexchange.whosaidit.game.domain.StatusUpdate;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  Logger logger = LoggerFactory.getLogger(this.getClass());

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

    RoundStartedEvent roundStartedEvent = new RoundStartedEvent(DomainEventType.RoundStartedEvent, round);

    logger.debug(roundStartedEvent.getDomainEventType().title);

    //send update to Twitter
    // Update TwitterService
    StringBuilder builder = new StringBuilder()
      .append("WhoSaidIt? New Round!")
      .append("\n")
      .append(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).format(ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York"))));

    CompletionStage<Response> twitterServiceResponse = twitterService.sendStatusUpdate(new StatusUpdate(builder.toString()))
      .thenApply(r -> {
        if (r.getStatus() != 201) {
          logger.error(String.valueOf(r.getStatus()));
        }
        return r;
      }).toCompletableFuture();


    //call Api Gateway
    CompletionStage<Response> apiGatewayResponse = apiGatewayService.sendRoundStartedEvent()
      .thenApply(r -> {
        if (r.getStatus() != 200) {
          logger.error(String.valueOf(r.getStatus()));
        }
        return r;
      }).toCompletableFuture();

    //log to History
    CompletionStage<Response> historyServiceResponse = historyService.sendEvent(roundStartedEvent)
      .thenApply(r -> {
        if (r.getStatus() != 200) {
          logger.error(String.valueOf(r.getStatus()));
        }
        return r;
      }).toCompletableFuture();

  }

}
