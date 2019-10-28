package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class NextQuoteEventHandler {

  @Inject
  @RestClient
  ApiGatewayService apiGatewayService;

  @Inject
  @RestClient
  TwitterService twitterService;

  @Inject
  @RestClient
  HistoryService historyService;

  public void reactiveHandle(Quote quote) {
  }

  public void handle(Quote quote) {

    // Update ApiGateway
    NextQuoteEvent nextQuoteEvent = new NextQuoteEvent();
    nextQuoteEvent.setQuote(quote);
    nextQuoteEvent.setDomainEventType(DomainEventType.NextQuoteEvent);
//    nextQuoteEvent.timestamp = Date.from(Instant.now());
    CompletionStage<Response> apiGatewayResponse = apiGatewayService.sendNextQuoteEvent(new NextQuoteEvent())

      .thenApply(r -> {
        if (r.getStatus() != 200) {
          throw new RuntimeException(String.valueOf(r.getStatus()));
        }
        return r;
      });

    // Update TwitterService
    StringBuilder builder = new StringBuilder()
      .append(quote.text)
      .append("\n")
      .append("A. Shakespeare\n")
      .append("B. Swarzeneggar\n")
      .append("C. Hamilton\n");

    CompletionStage<Response> twitterServiceResponse = twitterService.sendStatusUpdate(new StatusUpdate(builder.toString()))
      .thenApply(r -> {
        if(r.getStatus() != 201){
          throw new RuntimeException(String.valueOf(r.getStatus()));
        }
        return r;
      });

    // Log to HistoryService
    CompletionStage<Response> historyServiceResponse = historyService.sendEvent(nextQuoteEvent)
      .thenApply(r -> {
        if(r.getStatus() != 201){
          throw new RuntimeException(String.valueOf(r.getStatus()));
        }
        return r;
      });
  }

}
