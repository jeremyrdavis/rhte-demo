package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.*;
import io.reactivex.Observable;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Date;

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
    nextQuoteEvent.setEventType(EventType.NextQuoteEvent);
//    nextQuoteEvent.timestamp = Date.from(Instant.now());
    Response apiGatewayResponse = apiGatewayService.sendEvent(nextQuoteEvent);
    if (apiGatewayResponse.getStatus() != 200)
      throw new RuntimeException(String.valueOf(apiGatewayResponse.getStatus()));

    // Update TwitterService
    Response twitterServiceResponse = twitterService.sendStatusUpdate(new StatusUpdate(quote.toString()));
    if (twitterServiceResponse.getStatus() != 201)
      throw new RuntimeException(String.valueOf(twitterServiceResponse.getStatus()));

    // Log to HistoryService
    Response historyServiceResponse = historyService.sendEvent(nextQuoteEvent);
    if (historyServiceResponse.getStatus() != 200)
      throw new RuntimeException(String.valueOf(historyServiceResponse.getStatus()));
  }

}
