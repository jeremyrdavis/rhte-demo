package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.StatusUpdate;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@RegisterRestClient
public interface TwitterService {

  @POST
  @Path("/status")
  @Produces(MediaType.APPLICATION_JSON)
  public Response sendStatusUpdate(StatusUpdate statusUpdate);
}
