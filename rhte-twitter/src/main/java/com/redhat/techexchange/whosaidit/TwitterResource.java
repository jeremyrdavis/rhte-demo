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

  @Inject
  TwitterReader twitterReader;

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

  @POST
  @Path("/activate")
  public Response activateReader() {
    twitterReader.setActive(true);
    return Response.status(Response.Status.OK).build();
  }

  @POST
  @Path("/deactivate")
  public Response deactivateReader() {
    twitterReader.setActive(false);
    return Response.status(Response.Status.OK).build();
  }
}
