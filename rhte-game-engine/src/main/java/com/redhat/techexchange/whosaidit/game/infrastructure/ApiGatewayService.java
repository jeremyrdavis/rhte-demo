package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.Event;
import com.redhat.techexchange.whosaidit.game.domain.GameStartedEvent;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

@Path("/api/event")
@RegisterRestClient
public interface ApiGatewayService{

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response sendEvent(Event event);

  @POST
  @Path("/game/started")
  public CompletionStage<Response> sendGameStartedEvent(GameStartedEvent event);

}
