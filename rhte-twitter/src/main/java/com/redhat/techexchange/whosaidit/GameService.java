package com.redhat.techexchange.whosaidit;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/game")
@RegisterRestClient
public interface GameService {

  @POST
  @Path("/mentions")
  @Produces(MediaType.APPLICATION_JSON)
  public void reportMention();

}
