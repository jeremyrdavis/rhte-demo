package com.redhat.rhte.demos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/games")
public class GameResource {

  Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

  @Path("/")
  @GET
  public Response getAllGames() {

/*
    Game game1 = new Game();
    game1.name = "game1";
    game1.status = Game.GameStatus.ENDED;

    Game game2 = new Game();
    game2.name = "game2";
    game2.status = Game.GameStatus.ENDED;

    List<Game> games = new ArrayList<>(2);
    games.add(game1);
    games.add(game2);

*/
    List<Game> games = Game.listAll();
    LOGGER.debug("Retrieved " + games.size() + " records.");
    return Response.status(200).entity(games).build();
  }

  @POST
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response createGame(Game game) {

    game.status = Game.GameStatus.ACTIVE;
    game.persist();
    LOGGER.debug("created new game: " + game.toString());
    return Response.status(Response.Status.CREATED).entity(game).build();
  }

}
