package com.redhat.techexchange.whosaidit;

import twitter4j.Status;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TwitterResource {

  @Inject
  TwitterService twitterService;

  @POST
  @Path("/status")
  public Response publishStatus(PublishStatusCommand publishStatusCommand) {

    Status status = twitterService.updateStatus(publishStatusCommand.status);
    return Response.status(Response.Status.CREATED).build();
  }

}
