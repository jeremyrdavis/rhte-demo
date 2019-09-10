package com.redhat.rhte.demos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {

  Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

  @Path("/")
  @GET
  public Response getAllGames() {

    List<Game> games = Game.listAll();
    LOGGER.debug("Retrieved " + games.size() + " records.");
    return Response.status(200).entity(games).build();
  }

  @POST
  @Path("/")
  @Transactional
  public Response createGame(Game game) {

    game.status = Game.GameStatus.CREATED;
    game.persist();
    LOGGER.debug("created new game: " + game.toString());
    return Response.status(Response.Status.CREATED).entity(game).build();
  }

  @GET
  @Path("/{gameId}")
  public Response getGame(@PathParam("gameId") long id) {

    Game game = Game.findById(id);
    if (game == null) {

      return Response.status(Response.Status.NOT_FOUND).build();
    }else{

      return Response.status(Response.Status.FOUND).entity(game).build();
    }
  }

  @PUT
  @Path("/start/{gameId}")
  public Response startGame(@PathParam("gameId") long id) {

    Game game = Game.findById(id);
    game.status = Game.GameStatus.ACTIVE;
    game.persist();
    return Response.status(Response.Status.ACCEPTED).entity(game).build();
  }

}
