package com.redhat.rhte.demos;

import com.redhat.rhte.demos.domain.Game;
import com.redhat.rhte.demos.domain.GameNotFoundException;
import com.redhat.rhte.demos.domain.Referee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
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

  @Inject
  Referee referee;

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

    try {

      Game.create(game);
    } catch (Exception e) {

      return Response.status(Response.Status.BAD_REQUEST).entity(new WhoSaidItError(e.getMessage())).build();
    }

    LOGGER.debug("created new game: " + game.toString());
    return Response.status(Response.Status.CREATED).entity(game).build();
  }

  @GET
  @Path("/{gameId}")
  public Response getGame(@PathParam("gameId") long id) {

    try {

      Game game = referee.findById(id);
      return Response.status(Response.Status.FOUND).entity(game).build();
    } catch (GameNotFoundException e) {
      e.printStackTrace();
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @PUT
  @Path("/start/{gameId}")
  @Transactional
  public Response startGame(@PathParam("gameId") long id) {

    Game game = null;
    try {

      game = referee.startGame(id);
      LOGGER.info("Game #" + id + " started");
    } catch (GameNotFoundException e) {
      LOGGER.error(e.getMessage());
      return Response.status(Response.Status.NOT_FOUND).entity(e).build();
    }
    return Response.status(Response.Status.ACCEPTED).entity(game).build();
  }

  @PUT
  @Path("/stop/{gameId}")
  @Transactional
  public Response stopGame(@PathParam("gameId") long id) {

    Game game = null;
    try {

      game = referee.endGame(id);
      LOGGER.info("Game #" + id + " ended");
    } catch (GameNotFoundException e) {

      LOGGER.error(e.getMessage());
      return Response.status(Response.Status.NOT_FOUND).entity(e).build();
    }
    return Response.status(Response.Status.ACCEPTED).entity(game).build();
  }

  @POST
  @Path("/rounds/start/{gameId}")
  @Transactional
  public Response startRound(@PathParam("gameId") long id) {

    try {

      Game game = referee.startRound(id);
      LOGGER.info("Round started for Game #" + id);
      return Response.status(Response.Status.ACCEPTED).entity(game).build();
    } catch (GameNotFoundException e) {

      LOGGER.error(e.getMessage());
      return Response.status(Response.Status.NOT_FOUND).entity(e).build();
    }
  }

  @PUT
  @Path("/rounds/stop/{gameId}/{roundId}")
  @Transactional
  public Response stopRound(@PathParam("gameId") long gameId, @PathParam("roundId") long roundId) {

    Game game = Game.findById(gameId);
    game.stopRound();
    return Response.status(Response.Status.ACCEPTED).entity(game).build();
  }
}
