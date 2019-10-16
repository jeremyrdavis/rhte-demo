package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.NextQuoteEvent;
import com.redhat.techexchange.whosaidit.game.domain.Quote;
import com.redhat.techexchange.whosaidit.game.domain.RoundStartedEvent;
import com.redhat.techexchange.whosaidit.game.domain.StatusUpdate;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

@ApplicationScoped
public class NextQuestionHandler {
  private static final ApiGatewayService API_GATEWAY_CLIENT =
    RestClientBuilder.newBuilder()
      .baseUri(URI.create("http://localhost:8091"))
      .build(ApiGatewayService.class);

  private final static HistoryService HISTORY_SERVICE_CLIENT =
    RestClientBuilder.newBuilder()
      .baseUri(URI.create("http://localhost:8092"))
      .build(HistoryService.class);

  private final static TwitterService TWITTER_SERVICE_CLIENT =
    RestClientBuilder.newBuilder()
      .baseUri(URI.create("http://localhost:8093"))
      .build(TwitterService.class);

  void handle(NextQuoteEvent event) {

    // Update TwitterService
    StringBuilder builder = new StringBuilder()
      .append(event.getQuote().text)
      .append("\n")
      .append("A. Shakespeare\n")
      .append("B. Swarzeneggar\n")
      .append("C. Hamilton\n")
      .append(UUID.randomUUID());

    final CountDownLatch latch = new CountDownLatch(3);
    final AtomicReference<Throwable> throwable = new AtomicReference<>();

    BiConsumer<Response, Throwable> consumer = (r, t) -> {
      if (t != null) {
        throwable.set(t);
      }
      latch.countDown();
    };

    System.out.println("NextQuestionHandler sending\n" + event.getQuote().text);

    API_GATEWAY_CLIENT.sendNextQuoteEvent(event.toString()).whenCompleteAsync(consumer);
    HISTORY_SERVICE_CLIENT.sendEvent(event).whenCompleteAsync(consumer);
    TWITTER_SERVICE_CLIENT.sendStatusUpdate(new StatusUpdate(builder.toString())).whenCompleteAsync(consumer);

    try {
      latch.await();
    } catch (InterruptedException ex) {
      throw new WebApplicationException(ex, 500);
    }

    Throwable t = throwable.get();
    if (t != null) {
      System.out.println(t);
/*
      throw new WebApplicationException("Failure in downstream service",
        t, 500);
*/
    }
  }
}
