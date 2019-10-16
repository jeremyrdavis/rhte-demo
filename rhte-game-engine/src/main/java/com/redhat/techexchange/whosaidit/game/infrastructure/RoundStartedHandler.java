package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.RoundStartedEvent;
import com.redhat.techexchange.whosaidit.game.domain.StatusUpdate;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

@ApplicationScoped
public class RoundStartedHandler {

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

  void handle(RoundStartedEvent event) {

    final CountDownLatch latch = new CountDownLatch(3);
    final AtomicReference<Throwable> throwable = new AtomicReference<>();

    BiConsumer<Response, Throwable> consumer = (r, t) -> {
      if (t != null) {
        throwable.set(t);
      }
      latch.countDown();
    };

    API_GATEWAY_CLIENT.sendRoundStartedEvent().whenCompleteAsync(consumer);
    HISTORY_SERVICE_CLIENT.sendEvent(event).whenCompleteAsync(consumer);
    TWITTER_SERVICE_CLIENT.sendStatusUpdate(new StatusUpdate(new StringBuilder()
      .append("WhoSaidIt? Round Started!")
      .append("\n")
      .append(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).format(ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("America/New_York")))).toString()
    )).whenCompleteAsync(consumer);

    try {
      latch.await();
    } catch (InterruptedException ex) {
      throw new WebApplicationException(ex, 500);
    }

    Throwable t = throwable.get();
    if (t != null) {
      throw new WebApplicationException("Failure in downstream service",
        t, 500);
    }
  }
}
