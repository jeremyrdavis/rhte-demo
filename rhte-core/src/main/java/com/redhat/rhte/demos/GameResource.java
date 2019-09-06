package com.redhat.rhte.demos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {

  Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

  @Inject
  GameManager gameManager;

  @Path("/")
  @GET
  public Response getAllGames() {

    List<Game> games = Game.listAll();
    LOGGER.debug("Retrieved " + games.size() + " records.");
    return Response.status(200).entity(games).build();
  }

  @Path("/start")
  @POST
  public Response startGame() {

    Game game = gameManager.startGame();
    return Response.status(Response.Status.CREATED).entity(game).build();
  }
}
