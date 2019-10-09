package com.redhat.techexchange.whosaidit;

import twitter4j.Status;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

  @GET
  @Path("/replies/{since}")
  public Response getReplies(@PathParam("since") long sinceId) {

    List<Status> replies = twitterService.getReplies(sinceId);
    return Response.status(Response.Status.OK).entity(replies).build();
  }

}
