package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.Event;
import com.redhat.techexchange.whosaidit.game.domain.GameStartedEvent;
import com.redhat.techexchange.whosaidit.game.domain.NextQuoteEvent;
import com.redhat.techexchange.whosaidit.game.domain.RoundStartedEvent;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

@Path("/api")
@RegisterRestClient
public interface ApiGatewayService{

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public CompletionStage<Response> sendEvent(Event event);

  @POST
  @Path("/event/game/start")
  public CompletionStage<Response> sendGameStartedEvent(GameStartedEvent event);

  @POST
  @Path("/event/quote")
  public CompletionStage<Response> sendNextQuoteEvent(NextQuoteEvent event);

  @POST
  @Path("/event/round/start")
  public CompletionStage<Response> sendRoundStartedEvent(RoundStartedEvent event);

}
